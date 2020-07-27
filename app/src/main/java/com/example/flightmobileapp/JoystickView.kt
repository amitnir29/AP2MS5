package com.example.flightmobileapp

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
import android.util.Rational
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.SeekBar

/**
 * This class describes a joystick, who can move in two axises and computes the relative
 * values according to the location of the knob.
 */

class JoystickView : SurfaceView, SurfaceHolder.Callback, View.OnTouchListener {

    // The members that defines the joystick,
    private var centerX: Float = 0f;
    private var centerY: Float = 0f;
    private var baseRadius: Float = 0f;
    private var topRadius: Float = 0f;

    // Is the joystick moving
    private var inmove: Boolean = false;

    private var ratioDraw: Float = 30f;

    // The server communication object.
    private var serverCommunication: ServerCommunication = ServerCommunication.getInstance();


    constructor(context: Context) : super(context) {
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    /**
     * When the surface is created, this method will be called and will set up the dimensions
     *  and draw the initial position of the joystick.
     */
    override fun surfaceCreated(holder: SurfaceHolder?) {
        dimensionsSetUp();
        drawJoystick(centerX, centerY);
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int,
                                       height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    /**
     * A function which setups the dimensions.
     */
    private fun dimensionsSetUp() {
        centerX = (getWidth() / 2).toFloat();
        centerY = (getHeight() / 2).toFloat();
        baseRadius = (Math.min(getWidth(), getHeight()) / 3).toFloat();
        topRadius = (Math.min(getWidth(), getHeight()) / 5).toFloat();
    }

    /**
     * A function that draws the joystick in newX, newY.
     */
    private fun drawJoystick(newX: Float, newY: Float) {
        if (holder.surface.isValid) {
            serverCommunication.aileron = computeRelX(newX);
            serverCommunication.elevator = computeRelY(newY);

            val myCanvas: Canvas = holder.lockCanvas();
            val color: Paint = Paint();

            // Clear the canvas.
            myCanvas.drawColor(Color.WHITE, PorterDuff.Mode.SCREEN);

            val move: Float = -110.0F;


            val hypo: Float = Math.sqrt((((newX - centerX) * (newX - centerX))
                    + ((newY - centerY) * (newY - centerY))).toDouble()).toFloat();
            val cos: Float = (newX - centerX) / hypo;
            val sin: Float = (newY - centerY) / hypo;

            // Draw the base circle without shading.
            color.setARGB(255, 50, 50, 50);
            myCanvas.drawCircle(centerX, centerY + move, baseRadius, color);

            // Shading the base.
            for (i in 1..(baseRadius / ratioDraw).toInt()) {
                color.setARGB((150 / i).toInt(), 255, 0, 0);
                myCanvas.drawCircle((newX - cos * hypo * (ratioDraw / baseRadius) * i).toFloat(),
                        (newY - sin * hypo * (ratioDraw / baseRadius) * i).toFloat() + move,
                        (i * (topRadius * ratioDraw / baseRadius)).toFloat(), color);
            }

            // Draw the top circle without shading.
            color.setARGB(255, 0, 0, 255);
            myCanvas.drawCircle(newX, newY + move, topRadius, color);

            // Shading the top.
            for (i in 1..(topRadius / ratioDraw).toInt()) {
                color.setARGB(255, (i * (255 * ratioDraw / topRadius)).toInt(),
                        (i * (255 * ratioDraw / topRadius)).toInt(), 255);
                myCanvas.drawCircle(newX, newY + move,
                        ((topRadius - i.toFloat() * ratioDraw / 2) / 1.3).toFloat(), color);
            }

            // Post the changes.
            holder.unlockCanvasAndPost(myCanvas);
        }
    }

    /**
     * A function which being called each time someone touches the screen.
     */
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null && v.equals(this)) {

            // Moving the joystick.
            if (event != null && event.action != MotionEvent.ACTION_UP) {

                val checkX: Float = event.x - centerX;
                val checkY: Float = event.y - centerY;

                val radius: Float = Math.sqrt((checkX * checkX).toDouble() +
                        (checkY * checkY).toDouble()).toFloat();

                // Inside the borders.
                if (radius <= baseRadius) {
                    inmove = true;
                    drawJoystick(event.x, event.y);
                }
                // Need to calculate the relative place.
                else if (inmove) {
                    // Relative position.
                    val ratio: Float = baseRadius / radius;
                    val consX: Float = centerX + (event.x - centerX) * ratio;
                    val consY: Float = centerY + (event.y - centerY) * ratio;

                    drawJoystick(consX, consY);

                }
            }
            // Stop moving the joystick and returning it to center.
            else {
                drawJoystick(centerX, centerY);
                inmove = false;
            }
        }
        return true;
    }

    /**
     * Compute the relative position of X on the screen.
     */
    private fun computeRelX(x: Float): Float {
        return ((x - centerX) / baseRadius).toFloat();
    }

    /**
     * Compute the relative position of Y on the screen.
     */
    private fun computeRelY(y: Float): Float {
        // Give negative sign because the axis is top to bottom.
        return -((y - centerY) / baseRadius).toFloat();
    }
}
