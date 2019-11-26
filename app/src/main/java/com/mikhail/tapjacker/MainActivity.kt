package com.mikhail.tapjacker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

private const val REQUEST_CODE = 100

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkDrawOverlayPermission()
    }

    private fun checkDrawOverlayPermission() {
        if (!Settings.canDrawOverlays(applicationContext)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, REQUEST_CODE)
        } else {
            startServiceAndFinish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startServiceAndFinish()
            }
        }
    }

    private fun startServiceAndFinish() {
        val intent = Intent(this, OverlayService::class.java)
        if (!stopService(intent)) {
            startService(intent)
        }
        finish()
    }
}
