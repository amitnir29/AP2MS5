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
import android.util.Rational
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.SeekBar

/**
 * TODO: document your custom view class.
 */





class JoystickView : SurfaceView, SurfaceHolder.Callback, View.OnTouchListener {

    private var centerX : Float = 0f;
    private var centerY : Float = 0f;
    private var baseRadius : Float = 0f;
    private var topRadius : Float = 0f;

    private var inmove : Boolean = false;

    private var relX : Float = 0f;
    private var relY : Float = 0f;


    private  var ratioDraw : Float = 30f;

    private var serverCommunication : ServerCommunication = ServerCommunication.getInstance();


    constructor(context: Context) : super(context) {
        //init(null, 0)
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        //init(attrs, 0)
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        //init(attrs, defStyle)
        holder.addCallback(this);
        setOnTouchListener(this);
    }

    public override fun surfaceCreated(holder : SurfaceHolder?) {
        dimensionsSetUp();
        drawJoystick(centerX, centerY);
    }

    public override fun surfaceChanged(holder : SurfaceHolder?, format : Int, width : Int, height : Int) {

    }

    public override fun surfaceDestroyed(holder : SurfaceHolder?) {

    }

    private fun dimensionsSetUp() {
        centerX = (getWidth() / 2).toFloat();
        centerY = (getHeight() / 2).toFloat();
        baseRadius = (Math.min(getWidth(), getHeight()) / 3).toFloat();
        topRadius = (Math.min(getWidth(), getHeight()) / 5).toFloat();
    }

    private fun drawJoystick(newX : Float, newY : Float) {
        if (holder.surface.isValid) {
            var myCanvas : Canvas = holder.lockCanvas();
            var color : Paint = Paint();

            // Clear the canvas.
            //myCanvas.drawColor(Color.BLACK, PorterDuff.Mode.CLEAR);
            myCanvas.drawColor(Color.WHITE, PorterDuff.Mode.SCREEN);

            var move : Float = -110.0F;


            var hypo : Float = Math.sqrt((((newX - centerX) * (newX - centerX))
                        + ((newY - centerY) * (newY - centerY))).toDouble()).toFloat();
            var cos : Float = (newX - centerX) / hypo;
            var sin : Float = (newY - centerY) / hypo;

            // Draw the base circle without shading.
            color.setARGB(255, 50, 50, 50);
            myCanvas.drawCircle(centerX, centerY + move, baseRadius, color);

            // Shading the base.
            for (i in 1 .. (baseRadius / ratioDraw).toInt()) {
                color.setARGB((150 / i).toInt(), 255, 0, 0);
                myCanvas.drawCircle((newX - cos * hypo * (ratioDraw /baseRadius) * i).toFloat(),
                    (newY - sin * hypo * (ratioDraw / baseRadius) * i).toFloat() + move,
                    (i * (topRadius * ratioDraw / baseRadius)).toFloat(), color);
            }

            // Draw the top circle without shading.
            color.setARGB(255, 0, 0, 255);
            myCanvas.drawCircle(newX, newY + move, topRadius, color);

            // Shading the top.
            for (i in 1 .. (topRadius / ratioDraw).toInt()) {
                color.setARGB(255, (i * (255 * ratioDraw / topRadius)).toInt(),
                    (i * (255 * ratioDraw / topRadius)).toInt(), 255);
                myCanvas.drawCircle(newX, newY + move,
                    ((topRadius - i.toFloat() * ratioDraw / 2)/1.3).toFloat(), color);
            }

            // Post the changes.
            holder.unlockCanvasAndPost(myCanvas);
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null && v.equals(this)) {

            // Moving the joystick.
            if(event != null && event.action != MotionEvent.ACTION_UP) {

                var checkX : Float = event.x - centerX;
                var checkY : Float = event.y - centerY;

                var radius : Float = Math.sqrt((checkX * checkX).toDouble() +
                    (checkY * checkY).toDouble()).toFloat();


                // Inside the borders.
                if (radius <= baseRadius) {
                    inmove = true;
                    serverCommunication?.aileron ?: computeRelX(event.x);
                    serverCommunication?.elevator ?: computeRelX(event.y);
                    drawJoystick(event.x, event.y);
                }
                // Need to calculate the relative place.
                else if (inmove) {
                    // Relative position.
                    var ratio : Float = baseRadius / radius;
                    var consX : Float = centerX + (event.x - centerX) * ratio;
                    var consY : Float = centerY + (event.y - centerY) * ratio;

                    drawJoystick(consX, consY);
                    serverCommunication?.aileron ?: computeRelX(consX);
                    serverCommunication?.elevator ?: computeRelX(consY);
                }
            }
            // Stop moving the joystick and returning it to center.
            else {
                //ObjectAnimator.ofFloat(this, )
                serverCommunication?.aileron ?: computeRelX(centerX);
                serverCommunication?.elevator ?: computeRelX(centerY);
                drawJoystick(centerX, centerY);
                inmove = false;
            }
        }
        return true;
    }

    private fun computeRelX(x : Float): Float {
        return ((x - centerX) / (2.0 * baseRadius)).toFloat();
    }

    private fun computeRelY(y : Float): Float {
        return ((y - centerY) / (2.0 * baseRadius)).toFloat();
    }
}
