package com.mikhail.tapjacker

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat.TRANSPARENT
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.*
import androidx.annotation.RequiresApi

private const val TAG = "ols"

class OverlayService : Service() {

    private lateinit var overlay: View

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val inflater =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            TYPE_APPLICATION_OVERLAY
        } else {
            TYPE_TOAST
        }

        val params = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            flags,
            FLAG_NOT_TOUCHABLE or FLAG_WATCH_OUTSIDE_TOUCH,
            TRANSPARENT
        )

        overlay = inflater.inflate(R.layout.service_view, null)
        overlay.setOnTouchListener { v, event ->
            Log.d(TAG, "onTouch...${event.rawY} ${event.rawX}  ${v.x}  ${v.y}")
            true
        }

        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).addView(overlay, params)
    }


    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
