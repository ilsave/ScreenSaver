package com.example.fullscreenshowcase

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService: Service() {
    private var mBinder: LocalBinder = LocalBinder()
    private var usageCount = 0

    inner class LocalBinder() : Binder() {
        fun getService(): MyService = this@MyService
    }

    public fun getUsageCount(): Int {
        Log.d("Ilsave", "count = $usageCount")
        usageCount++
        return usageCount
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("Ilsave", "Bind")
        return mBinder
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("Ilsave", "UnBind")
        return super.onUnbind(intent)
    }
}