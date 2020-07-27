package com.example.flightmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.flightmobileapp.databinding.FragmentLoginBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A [Fragment] for the login screen.
 */
class LoginFragment : Fragment() {
    // Scope and cancellation token for IO queries.
    private var databaseJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + databaseJob)

    // Binding object.
    private lateinit var binding: FragmentLoginBinding;


    /**
     * Called when view is created.
     * @param inflater an inflater.
     * @param container a container.
     * @param savedInstanceState saved instance used by the android architecture.
     * @return the root of the binding (the view).
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Set the binding object.
        binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
                R.layout.fragment_login, container, false)

        // Get a reference to the application and the database.
        val application = requireNotNull(this.activity).application
        val database = SavedURLsDatabase.getInstance(application)

        uiScope.launch {
            // Read database to the buttons.
            readDataBase(database)
        }

        // On click on connect button should (try to) start the simulator.
        binding.Connect.setOnClickListener { view: View ->
            uiScope.launch {
                startSimulator(database, view)
            }
        }

        return binding.root
    }


    /**
     * Called when the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()

        // cancel the cancellation token.
        databaseJob.cancel()
    }


    /**
     * Read elements from the database to the appropriate buttons.
     * @param database the database of the urls.
     */
    private suspend fun readDataBase(database: SavedURLsDatabase) {
        // Read the most 5 relevant urls from the database.
        var savedURLs: List<String>
        withContext(Dispatchers.IO) {
            savedURLs = database.getRelevants()
        }

        // Declare last element to iterate over.
        val last = savedURLs.size - 1

        // Iterate over all the relevant urls.
        for (i in 0..last) {
            // Get the corresponding button.
            val urlButton = (binding.URLButtons[i] as Button)

            // Set it as visible.
            urlButton.visibility = View.VISIBLE

            // Change the button text to the url.
            urlButton.text = savedURLs[i]

            // By clicking on the button the text box should show the corresponding url.
            urlButton.setOnClickListener {
                binding.URL.setText(savedURLs[i])
            }
        }
    }


    /**
     * Get an image from the server.
     * @param database the database of the urls.
     * @param view the view object.
     * @return the image from the server if succeeded.
     */
    private suspend fun startSimulator(database: SavedURLsDatabase, view: View) {
        // Get the uri inserted by the user.
        val url: String = binding.URL.text.toString()

        // Write the new url to the database.
        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.uriConnected(url)
            }
        }


        // If the url's prefix is a valid http/ https prefix:
        if (url.startsWith("http://")
                || url.startsWith("https://")) {

            // Initiate the api with the url.
            Api.init(url)

            // Get a screenshot from the server.
            val get = Api.api.getImg()

            try {
                // Wait for the query from the server to complete.
                get.await()

                // If succeeded.

                // Clean the text box.
                binding.URL.setText("")

                // Move to the simulator frame.
                val bundle = bundleOf("url" to url)
                view.findNavController().navigate(R.id.action_loginFragment_to_simulatorFragment,
                        bundle)
            } catch (e: Exception) {
                // Notify the user that failed connecting to the server.
                val message = "Failed to connect to server."
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, message, duration)

                toast.show()
            }
        } else {
            // Notify the user that the url is invalid.
            val message = "Illegal server path"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(context, message, duration)

            toast.show()

        }
    }
}