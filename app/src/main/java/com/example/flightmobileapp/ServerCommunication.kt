package com.example.flightmobileapp

import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
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

/**
 * This class is responsible to send values to the server, if needed.
 */
class ServerCommunication {
    private var uri : String = ""
    private var context:Context;
    private var simulatorJob: Job
    private var scope: CoroutineScope

    private constructor(url : String, context: Context) {
        uri = url
        this.context = context

        simulatorJob = Job()
        scope = CoroutineScope(Dispatchers.Main + simulatorJob)
    }


    /**
     * To make the class a "singleton" class.
     */
    companion object {
        @Volatile
        var INSTANCE: ServerCommunication? = null

        /**
         * Create the object.
         */
        fun create(url: String, context: Context) {
            INSTANCE = ServerCommunication(url, context)
        }

        /**
         * Return the instance of the object.
         */
        fun getInstance() :ServerCommunication{

            return INSTANCE!!
        }

        /**
         * Do we exist.
         */
        fun isNull():Boolean{
            return INSTANCE==null;
        }
    }

    /**
     * This is the aileron property.
     */
    var aileron : Float = 0f
    set(value) {
        // Update the "delta" - the change from last time sent to server.
        lAileron += value - field
        // Update the value.
        field = value
        // We updated our value, check if need to send to server.
        scope.launch {
            sendToServer()
        }
    }

    /**
     * This is the elevator property.
     */
    var elevator : Float = 0f
    set(value) {
        // Update the "delta" - the change from last time sent to server.
        lElevator += value - field
        // Update the value.
        field = value
        // We updated our value, check if need to send to server.
        scope.launch {
            sendToServer()
        }
    }

    /**
     * This is the rudder property.
     */
    var rudder : Float = 0f
    set(value) {
        // Update the "delta" - the change from last time sent to server.
        lRudder += value - field
        // Update the value.
        field = value
        // We updated our value, check if need to send to server.
        scope.launch {
            sendToServer()
        }
    }

    /**
     * This is the throttle property.
     */
    var throttle : Float = 0f
    set(value) {
        // Update the "delta" - the change from last time sent to server.
        lThrottle += value - field
        // Update the value.
        field = value
        // We updated our value, check if need to send to server.
        scope.launch {
            sendToServer()
        }
    }


    // These all are the "delta" to each property - the change from last time sent to server.
    private var lAileron : Float = 0f;
    private var lElevator : Float = 0f;
    private var lRudder : Float = 0f;
    private var lThrottle : Float = 0f;


    /**
     * This function is checking if need to send the new values to the server (if there was more
     *  than 1% change).
     */
    private suspend fun sendToServer() {
        var flag : Boolean = false
        // Check each value if changed more than 1%.
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


        // If need to send to the server.
        if (flag) {
            jsonandcomm();
        }
    }

    /**
     * This function sends the new values to the server.
     */
    private suspend fun jsonandcomm() {
        // The url.
        val url = "$uri/command/"
        // Create a command object.
        val command: Vals = Vals(aileron,rudder,elevator,throttle);
        // Send via API.
        val post = Api.api.postVals(command)


        // Try to send and if failed than toast a message.
        try {
            post.await()
        }
        catch (e: Exception) {
            val message = "Failed to get image from the server."
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(context, message, duration)

            toast.show()
        }
    }
}