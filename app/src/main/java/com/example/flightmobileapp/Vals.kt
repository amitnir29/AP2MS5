package com.example.flightmobileapp

import com.google.gson.annotations.SerializedName

data class Vals(val a: Float = 0f, val r: Float = 0f, val e: Float = 0f, val t: Float = 0f) {

    @SerializedName("aileron") val aileron : Float = a

    @SerializedName("rudder") val rudder : Float = r

    @SerializedName("elevator") val elevator : Float = e

    @SerializedName("throttle") val throttle : Float = t

}