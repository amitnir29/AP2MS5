package com.example.flightmobileapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.math.abs

class ServerCommunication {
    private var uri : String = ""
    private lateinit var context:Context;
    private constructor(url : String, context: Context) {
        uri = url
        this.context = context
    }

    companion object {
        private var INSTANCE: ServerCommunication? = null

        public fun create(url: String, context: Context) {
            INSTANCE = ServerCommunication(url, context)
        }

        public fun getInstance() :ServerCommunication{

            return INSTANCE!!
        }
    }

    public var aileron : Float = 0f
    set(value) {
        lAileron += value - field
        field = value
        sendToServer()
    }
    public var elevator : Float = 0f
    set(value) {
        lElevator += value - field
        field = value
        sendToServer()
    }
    public var rudder : Float = 0f
    set(value) {
        lRudder += value - field
        field = value
        sendToServer()
    }
    public var throttle : Float = 0f
    set(value) {
        lThrottle += value - field
        field = value
        sendToServer()
    }
    private var lAileron : Float = 0f;
    private var lElevator : Float = 0f;
    private var lRudder : Float = 0f;
    private var lThrottle : Float = 0f;


    private fun sendToServer() {
        var flag : Boolean = false
        if (abs(lThrottle) >= 0.01) {
            flag = true
            lThrottle = 0F
        }
        if (abs(lElevator) >= 0.02) {
            flag = true
            lElevator = 0F
        }
        if (abs(lRudder) >= 0.02) {
            flag = true
            lRudder = 0F
        }
        if (abs(lAileron) >= 0.02) {
            flag = true
            lAileron = 0F
        }


        if (flag) {
            jsonandcomm();
        }
    }

    private fun jsonandcomm() {
        //TODO
        val url = "$uri/command/"
        val command: Vals = Vals(aileron,rudder,elevator,throttle);

        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val api = retrofit.create(Api::class.java)

        api.postVals(command).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                val message = "Failed to get image from the server."
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, message, duration)

                toast.show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }
        })

    }




    /*Throws(IOException::class, JSONException::class)
    private suspend fun httpPost(myUrl: String): String {

        val result = withContext(Dispatchers.IO) {
            val url = URL(myUrl)
            // 1. create HttpURLConnection
            val conn = url.openConnection() as HttpsURLConnection
            conn.requestMethod = "POST"
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8")

            // 2. build JSON object
            val jsonObject = buidJsonObject()

            // 3. add JSON content to POST request body
            setPostRequestContent(conn, jsonObject)

            // 4. make POST request to the given URL
            conn.connect()

            // 5. return response message
            conn.responseMessage + ""
        }
        return result
    }

    public fun send(view: View) {
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        // clear text result
        tvResult.setText("")

        if (checkNetworkConnection())
            lifecycleScope.launch {
                val result = httpPost("https://hmkcode-api.appspot.com/rest/link/add")
                tvResult.setText(result)
            }
        else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show()

    }*/
}