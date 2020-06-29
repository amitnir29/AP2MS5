package com.example.flightmobileapp

import android.view.View
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import kotlin.math.abs

class ServerCommunication {

    private var uri : String = ""
    constructor(url : String) {
        uri = url
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
        /*val con: HttpURLConnection = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json; utf-8")*/

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