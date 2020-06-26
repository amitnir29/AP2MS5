package com.example.flightmobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView

class MainActivity : AppCompatActivity() {
    ///lateinit var simulatorFragment: SimulatorFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ///simulatorFragment = SimulatorFragment()
        setContentView(R.layout.activity_main)
        ////////
        ///var joystick : JoystickView = JoystickView(this);
        //setContentView(joystick);
        //LoginFragment.newInstance()
        //setContentView(R.layout.fragment_login)
    }
}