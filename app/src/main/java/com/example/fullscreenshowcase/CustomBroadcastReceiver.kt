package com.example.fullscreenshowcase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi

class CustomBroadcastReceiver: BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context?, intent: Intent?) {
        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isDeviceIdleMode) {
            // the device is now in doze mode
            Log.d("Ilsave", "doze mode")
        } else {
            // the device just woke up from doze mode
            Log.d("Ilsave", "not in doze mode anymore")
        }
    }
}