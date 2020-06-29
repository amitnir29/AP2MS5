package com.example.flightmobileapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
 * A simple [Fragment] subclass.
 */
class SimulatorFragment : Fragment() {
    private lateinit var binding: FragmentSimulatorBinding
    private lateinit var simulatorJob: Job

    private var uri: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
        R.layout.fragment_simulator, container, false)

        return binding.root
    }


    override fun onStart() {
        super.onStart()

        uri = arguments?.getString("url")

        rudderSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rudderValue.text = (progress.toFloat() / 100.0).toString();
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        simulatorJob = Job()

        val scope = CoroutineScope(Dispatchers.Main + simulatorJob)

        scope.launch {
            imageAsking(100)
        }
    }


    override fun onStop() {
        super.onStop()
        simulatorJob.cancel()
    }

    //TODO fix backwards disconnection


    private suspend fun imageAsking(delayTime: Long) {
        //binding.textView.text = uri

        withContext(Dispatchers.Main) {
            while (true) {
                if (uri != null) {
                    val url = "$uri/screenshot/"
                    getImageFromServer(url)
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
    private fun getImageFromServer(url: String) {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val api = retrofit.create(Api::class.java)

        api.getImg().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {

                if (response.isSuccessful) {
                    // Save the response of the server as a byte stream.
                    val res = response.body()?.byteStream()
                    val picture = BitmapFactory.decodeStream(res!!)

                    binding.simulatorView.setImageBitmap(picture)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to get image from the server."
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, message, duration)

                toast.show()
            }
        })
    }
}