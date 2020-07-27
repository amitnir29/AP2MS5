package com.example.flightmobileapp

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    /**
     * A get function.
     * Ask the server for an image.
     */
    @GET("/screenshot")
    fun getImg(): Deferred<ResponseBody>


    /**
     * A post function.
     * Post values to the command section of the server.
     * @param vals the values to send in the body.
     */
    @POST("/api/command")
    fun postVals(@Body vals: Vals): Deferred<ResponseBody>



    companion object {
        // Access to the api of the retrofit.
        lateinit var api: Api


        /**
         * Initialize the api.
         * @prama url the url to connect the api to.
         */
        fun init(url: String) {
            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(CoroutineCallAdapterFactory())
                    .baseUrl(url)
                    .build()

            api = retrofit.create(Api::class.java)
        }
    }
}
