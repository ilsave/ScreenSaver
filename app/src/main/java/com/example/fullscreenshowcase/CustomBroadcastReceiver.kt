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
        Log.d("Power", "You have been here in doze")

        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager
        if (pm.isDeviceIdleMode) {
            // the device is now in doze mode
            Log.d("Power", "doze mode")
        } else {
            // the device just woke up from doze mode
            Log.d("Power", "not in doze mode anymore")
        }
    }
}