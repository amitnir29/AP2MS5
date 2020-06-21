package com.example.flightmobileapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View

/**
 * TODO: document your custom view class.
 */
class JoystickView : SurfaceView, SurfaceHolder.Callback, View.OnTouchListener {

    private var centerX : Float = 0f
    private var centerY : Float = 0f
    private var baseRadius : Float = 0f
    private var topRadius : Float = 0f

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
            myCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

            // Draw the base circle.
            color.setARGB(255, 50, 50, 50);
            myCanvas.drawCircle(centerX, centerY, baseRadius, color);

            // Draw the top circle.
            color.setARGB(255, 0, 0, 255);
            myCanvas.drawCircle(newX, newY, topRadius, color);

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
                    drawJoystick(event.x, event.y);
                }
                // Need to calculate the relative place.
                else {
                    var ratio : Float = baseRadius / radius;
                    var consX : Float = centerX + (event.x - centerX) * ratio;
                    var consY : Float = centerY + (event.y - centerY) * ratio;
                    /*var dx : Float = ((radius - baseRadius) / radius) * event.x;
                    var dy : Float = ((radius - baseRadius) / radius) * event.y;
                    var x : Float = event.x - dx;
                    var y : Float = event.y - dy;*/

                    drawJoystick(consX, consY);
                }
            }
            // Stop moving the joystick and returning it to center.
            else {
                drawJoystick(centerX, centerY);
            }
        }
        return true;
    }


    /*private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private var textPaint: TextPaint? = null
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.JoystickView, defStyle, 0
        )

        _exampleString = a.getString(
            R.styleable.JoystickView_exampleString
        )
        _exampleColor = a.getColor(
            R.styleable.JoystickView_exampleColor,
            exampleColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
            R.styleable.JoystickView_exampleDimension,
            exampleDimension
        )

        if (a.hasValue(R.styleable.JoystickView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                R.styleable.JoystickView_exampleDrawable
            )
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
        }

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint?.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        exampleString?.let {
            // Draw the text.
            canvas.drawText(
                it,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint
            )
        }

        // Draw the example drawable on top of the text.
        exampleDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }
    }*/
}
