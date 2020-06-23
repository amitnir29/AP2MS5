package com.example.flightmobileapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.flightmobileapp.databinding.FragmentSimulatorBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val URI: String = "place holder"

/**
 * A simple [Fragment] subclass.
 */
class SimulatorFragment : Fragment() {
    private lateinit var binding: FragmentSimulatorBinding
    private lateinit var simulatorJob: Job

    private lateinit var uri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uri = it.getString(URI)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<FragmentSimulatorBinding>(inflater,
        R.layout.fragment_simulator, container, false)

        binding.textView.text = uri
        return binding.root
    }


    override fun onStart() {
        super.onStart()

        val uri:String = "http://localhost:5410"

        simulatorJob = Job()

        val scope = CoroutineScope(Dispatchers.Main + simulatorJob)

        /*scope.launch {
            imageAsking(uri, 100)
        }*/
    }


    override fun onStop() {
        super.onStop()
        simulatorJob.cancel()
    }


    companion object {
        @JvmStatic
        fun newInstance(uri: String) =
                BlankFragment().apply {
                    arguments = Bundle().apply {
                        putString(URI, uri)
                    }
                }
    }


    private suspend fun imageAsking(uri: String, delayTime: Long) {
        while (true) {
            val url = uri + "screenshot/"

            withContext(Dispatchers.Main) {
                getImageFromServer(url)
            }

            delay(delayTime)
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
                if (response.isSuccessful()) {
                    // Save the response of the server as a byte stream.
                    val res = response.body()?.byteStream()
                    val picture = BitmapFactory.decodeStream(res!!)

                    binding.simulatorView.setImageBitmap(picture)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
}