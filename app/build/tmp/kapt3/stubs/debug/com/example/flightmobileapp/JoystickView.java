package com.example.flightmobileapp;

import java.lang.System;

/**
 * TODO: document your custom view class.
 */
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006B\u0017\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tB\u001f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\u001e\u001a\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\u0002J\u0010\u0010 \u001a\u00020\u000e2\u0006\u0010!\u001a\u00020\u000eH\u0002J\b\u0010\"\u001a\u00020#H\u0002J\u0018\u0010$\u001a\u00020#2\u0006\u0010%\u001a\u00020\u000e2\u0006\u0010&\u001a\u00020\u000eH\u0002J\u001c\u0010\'\u001a\u00020\u00122\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010*\u001a\u0004\u0018\u00010+H\u0016J*\u0010,\u001a\u00020#2\b\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010/\u001a\u00020\u000b2\u0006\u00100\u001a\u00020\u000b2\u0006\u00101\u001a\u00020\u000bH\u0016J\u0012\u00102\u001a\u00020#2\b\u0010-\u001a\u0004\u0018\u00010.H\u0016J\u0012\u00103\u001a\u00020#2\b\u0010-\u001a\u0004\u0018\u00010.H\u0016R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R(\u0010\u0018\u001a\u0004\u0018\u00010\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017@FX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/example/flightmobileapp/JoystickView;", "Landroid/view/SurfaceView;", "Landroid/view/SurfaceHolder$Callback;", "Landroid/view/View$OnTouchListener;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyle", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "baseRadius", "", "centerX", "centerY", "inmove", "", "ratioDraw", "relX", "relY", "value", "Lcom/example/flightmobileapp/ServerCommunication;", "serverCommunication", "getServerCommunication", "()Lcom/example/flightmobileapp/ServerCommunication;", "setServerCommunication", "(Lcom/example/flightmobileapp/ServerCommunication;)V", "topRadius", "computeRelX", "x", "computeRelY", "y", "dimensionsSetUp", "", "drawJoystick", "newX", "newY", "onTouch", "v", "Landroid/view/View;", "event", "Landroid/view/MotionEvent;", "surfaceChanged", "holder", "Landroid/view/SurfaceHolder;", "format", "width", "height", "surfaceCreated", "surfaceDestroyed", "app_debug"})
public final class JoystickView extends android.view.SurfaceView implements android.view.SurfaceHolder.Callback, android.view.View.OnTouchListener {
    private float centerX = 0.0F;
    private float centerY = 0.0F;
    private float baseRadius = 0.0F;
    private float topRadius = 0.0F;
    private boolean inmove = false;
    private float relX = 0.0F;
    private float relY = 0.0F;
    private float ratioDraw = 30.0F;
    @org.jetbrains.annotations.Nullable()
    private com.example.flightmobileapp.ServerCommunication serverCommunication;
    private java.util.HashMap _$_findViewCache;
    
    @org.jetbrains.annotations.Nullable()
    public final com.example.flightmobileapp.ServerCommunication getServerCommunication() {
        return null;
    }
    
    public final void setServerCommunication(@org.jetbrains.annotations.Nullable()
    com.example.flightmobileapp.ServerCommunication value) {
    }
    
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
    
    private final void dimensionsSetUp() {
    }
    
    private final void drawJoystick(float newX, float newY) {
    }
    
    @java.lang.Override()
    public boolean onTouch(@org.jetbrains.annotations.Nullable()
    android.view.View v, @org.jetbrains.annotations.Nullable()
    android.view.MotionEvent event) {
        return false;
    }
    
    private final float computeRelX(float x) {
        return 0.0F;
    }
    
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