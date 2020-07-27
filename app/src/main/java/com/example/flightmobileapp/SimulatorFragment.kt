package com.example.flightmobileapp

import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigator
import com.example.flightmobileapp.databinding.FragmentSimulatorBinding
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_simulator.*
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment for the main screen.
 */
class SimulatorFragment : Fragment() {
    // The binding object.
    private lateinit var binding: FragmentSimulatorBinding

    // The cancellation token.
    private lateinit var simulatorJob: Job

    // Used to communicate with the server.
    private var serverCommunication: ServerCommunication? = null;

    // The uri of the server.
    private var uri: String? = null

    // A toast object to notify messages to the user.
    private var toast: Toast? = null;


    /**
     * Called before the fragment is created.
     * @param savedInstanceState a bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);

        // Get the uri parameter passed by the navigation.
        uri = arguments?.getString("url")

        // Create a server communication instance if is null.
        if (ServerCommunication.isNull()) {
            ServerCommunication.create(uri!!, context!!);
        }
    }


    /**
     * Called before the view is created.
     * @param inflater an inflater.
     * @param container a container.
     * @param savedInstanceState saved instance used by the android architecture.
     * @return the root of the binding (the view).
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_simulator, container, false)

        return binding.root
    }


    /**
     * Called when the fragment is started.
     */
    override fun onStart() {
        super.onStart()

        // Get the instance of the server communication.
        serverCommunication = ServerCommunication.getInstance();

        // Style the rudders.
        rudderSeekBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        rudderSeekBar.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        rudderValue.text = serverCommunication!!.rudder.toString();

        throttleSeekBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        throttleSeekBar.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
        throttleValue.text = serverCommunication!!.throttle.toString();

        // Declare events of the rudders.
        rudderSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var cur: Float = ((progress.toFloat() - 100f) / 100f)
                rudderValue.text = cur.toString();
                serverCommunication?.rudder = cur;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        throttleSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var cur: Float = (progress.toFloat() / 100f)
                throttleValue.text = cur.toString();
                serverCommunication?.throttle = cur;
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        // Initiate the cancellation token and the scope.
        simulatorJob = Job()
        val scope = CoroutineScope(Dispatchers.Main + simulatorJob)

        // Start asking screenshots from the server.
        scope.launch {
            imageAsking(250)
        }
    }


    /**
     * Called when the fragment is stopped.
     */
    override fun onStop() {
        super.onStop()

        // Cancel the cancellation token.
        simulatorJob.cancel()
    }


    /**
     * Called when the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()

        // Cancel the cancellation token.
        simulatorJob.cancel()
    }


    /**
     * Send get queries (for screenshots) to the server.
     * @param delayTime a delay time (im milliseconds) between queries.
     */
    private suspend fun imageAsking(delayTime: Long) {
        withContext(Dispatchers.Main) {
            while (true) {
                if (uri != null) {
                    // Ask for a screenshot.
                    val url = "$uri/screenshot/"
                    getImageFromServer(url)

                    // Wait the delay time.
                    delay(delayTime)
                }
            }
        }
    }


    /**
     * Get an image from the server.
     * @param url the url to ask image from.
     * @return the image from the server if succeeded.
     */
    private suspend fun getImageFromServer(url: String) {
        // Ask for an image.
        val get = Api.api.getImg()

        // Cancel the previous notify to the server.
        if (toast != null) {
            toast!!.cancel();
        }

        try {
            // Try to get a response from the server.
            val response = get.await()
            val res = response.byteStream()
            val picture = BitmapFactory.decodeStream(res)

            // Change the picture on the screen to the given picture from the server.
            binding.simulatorView.setImageBitmap(picture)
        } catch (e: CancellationException) {
            // If the function is cancelled should simply stop.
        } catch (e: Exception) {
            // If another exception occurred notify the user.

            val message = "Failed to get image from the server."
            val duration = Toast.LENGTH_SHORT

            toast = Toast.makeText(context, message, duration)

            toast!!.show()
        }
    }
}