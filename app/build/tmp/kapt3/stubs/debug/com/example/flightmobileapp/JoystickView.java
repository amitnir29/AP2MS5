package com.example.flightmobileapp;

import java.lang.System;

/**
 * This class describes a joystick, who can move in two axises and computes the relative
 * values according to the location of the knob.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tB\u001f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u0017\u001a\u00020\u000e2\u0006\u0010\u0018\u001a\u00020\u000eH\u0002J\u0010\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000eH\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u001c\u0010 \u001a\u00020\u00122\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J*\u0010%\u001a\u00020\u001c2\b\u0010&\u001a\u0004\u0018\u00010\'2\u0006\u0010(\u001a\u00020\u000b2\u0006\u0010)\u001a\u00020\u000b2\u0006\u0010*\u001a\u00020\u000bH\u0016J\u0012\u0010+\u001a\u00020\u001c2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0016J\u0012\u0010,\u001a\u00020\u001c2\b\u0010&\u001a\u0004\u0018\u00010\'H\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/example/flightmobileapp/JoystickView;", "Landroid/view/SurfaceView;", "Landroid/view/SurfaceHolder$Callback;", "Landroid/view/View$OnTouchListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "baseRadius", "", "centerX", "centerY", "inmove", "", "ratioDraw", "serverCommunication", "Lcom/example/flightmobileapp/ServerCommunication;", "topRadius", "computeRelX", "x", "computeRelY", "y", "dimensionsSetUp", "", "drawJoystick", "newX", "newY", "onTouch", "v", "Landroid/view/View;", "event", "Landroid/view/MotionEvent;", "surfaceChanged", "holder", "Landroid/view/SurfaceHolder;", "format", "width", "height", "surfaceCreated", "surfaceDestroyed", "app_debug"})
public final class JoystickView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback, android.view.View.OnTouchListener {
    private float centerX = 0.0F;
    private float centerY = 0.0F;
    private float baseRadius = 0.0F;
    private float topRadius = 0.0F;
    private boolean inmove = false;
    private float ratioDraw = 30.0F;
    private com.example.flightmobileapp.ServerCommunication serverCommunication;
    private java.util.HashMap _$_findViewCache;
    
    /**
     * When the surface is created, this method will be called and will set up the dimensions
     * and draw the initial position of the joystick.
     */
    @java.lang.Override()
    public void surfaceCreated(@org.jetbrains.annotations.Nullable()
    android.view.SurfaceHolder holder) {
    }
    
    @java.lang.Override()
    public void surfaceChanged(@org.jetbrains.annotations.Nullable()
    android.view.SurfaceHolder holder, int format, int width, int height) {
    }
    
    @java.lang.Override()
    public void surfaceDestroyed(@org.jetbrains.annotations.Nullable()
    android.view.SurfaceHolder holder) {
    }
    
    /**
     * A function which setups the dimensions.
     */
    private final void dimensionsSetUp() {
    }
    
    /**
     * A function that draws the joystick in newX, newY.
     */
    private final void drawJoystick(float newX, float newY) {
    }
    
    /**
     * A function which being called each time someone touches the screen.
     */
    @java.lang.Override()
    public boolean onTouch(@org.jetbrains.annotations.Nullable()
    android.view.View v, @org.jetbrains.annotations.Nullable()
    android.view.MotionEvent event) {
        return false;
    }
    
    /**
     * Compute the relative position of X on the screen.
     */
    private final float computeRelX(float x) {
        return 0.0F;
    }
    
    /**
     * Compute the relative position of Y on the screen.
     */
    private final float computeRelY(float y) {
        return 0.0F;
    }
    
    public JoystickView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    public JoystickView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public JoystickView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.util.AttributeSet attrs, int defStyle) {
        super(null);
    }
}