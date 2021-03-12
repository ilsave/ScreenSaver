package com.example.fullscreenshowcase

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.PowerManager
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

class MyService: Service() {
    private var mBinder: LocalBinder = LocalBinder()
    private var usageCount = 0
    val receiver = PowerConnectionReceiver()



    private val CHANNEL_ID_DEFAULT_PRIORITY = "1"

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        val ifilter = IntentFilter()
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED)
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        ifilter.addAction(Intent.ACTION_SCREEN_OFF)
        registerReceiver(receiver, ifilter)

        Log.d("Power", "You have been here")
        val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                    CHANNEL_ID_DEFAULT_PRIORITY, "My channel default priority",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "My channel description"
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(
                this, CHANNEL_ID_DEFAULT_PRIORITY
        )
                .setContentTitle("We are listening to your device!")
                .setContentText("Waiting for device to fall in doze mode!")
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
        val notification: Notification = builder.build()
        startForeground(5, notification)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

}