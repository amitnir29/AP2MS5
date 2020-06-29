package com.example.flightmobileapp

import kotlinx.coroutines.Deferred
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Api {
    @GET("/screenshot")
    fun getImg(): Call<ResponseBody>

    @POST("/api/command")
    fun postVals(@Body vals: Vals): Call<ResponseBody>
}
