package com.example.flightmobileapp

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.google.gson.GsonBuilder
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream

/**
 * TODO: document your custom view class.
 */
class PictureView : SurfaceView {

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
    }


    /**
     * Draw a given image on the scree.
     * @param img input stream representing the image.
     * @param x distance from left.
     * @param y distance from top.
     */
    private fun drawImageToScreen(img: InputStream?, x: Float, y: Float) {
        if (img != null) {
            // Decode the stream into a bitmap object.
            val bitmap = BitmapFactory.decodeStream(img);

            if (holder.surface.isValid) {
                // Get the canvas to draw the picture on.
                val myCanvas: Canvas = holder.lockCanvas();

                // Set color to null.
                val color: Paint? = null;

                // Draw the image.
                myCanvas.drawBitmap(bitmap, x, y, color);
            }
        }
    }


    /**
     * Get an image from the server.
     * @param ip the ip of the server.
     * @param port the port of the server.
     * @return the image from the server if succeeded.
     */
    private fun getImageFromServer(ip: String, port: Int) {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://$ip:$port/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        val api = retrofit.create(Api::class.java)

        // The result to return later.
        var res : InputStream? = null;

        api.getImg().enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
            ) {
                if (response.isSuccessful()) {
                    // Save the response of the server as a byte stream.
                    res = response.body()?.byteStream()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
}
