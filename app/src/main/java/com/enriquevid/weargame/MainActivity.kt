package com.enriquevid.weargame

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.core.view.InputDeviceCompat
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewConfigurationCompat
import kotlin.math.sign


class MainActivity : Activity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var view: GameView = GameView(this)
        view.isFocusable = true;
        view.isClickable = true;

        view.setOnGenericMotionListener { v, ev ->
            if (ev.action == MotionEvent.ACTION_SCROLL &&
                    ev.isFromSource(InputDeviceCompat.SOURCE_ROTARY_ENCODER)
            ) {
                // Don't forget the negation here
                val delta = -ev.getAxisValue(MotionEventCompat.AXIS_SCROLL) *
                        ViewConfigurationCompat.getScaledVerticalScrollFactor(
                                ViewConfiguration.get(this), this
                        )
                // Swap these axes to scroll horizontally instead
                //v.scrollBy(0, delta.roundToInt())

                view.rotaryEvent += sign(delta)
                true

            } else {
                false
            }
        }

        setContentView(view)
        view.vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // Enables Always-on
        //setAmbientEnabled()
    }




}

