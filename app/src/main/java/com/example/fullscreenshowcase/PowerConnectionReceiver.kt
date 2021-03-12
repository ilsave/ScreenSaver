package com.example.fullscreenshowcase

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.BatteryManager.BATTERY_PLUGGED_AC
import android.os.BatteryManager.BATTERY_PLUGGED_USB
import android.os.Build
import android.os.PowerManager
import android.os.PowerManager.WakeLock
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PowerConnectionReceiver: BroadcastReceiver() {

    private var disconnected = false

    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onReceive(context: Context?, intent: Intent?) {

        when(intent?.action){
            Intent.ACTION_POWER_CONNECTED -> {
                disconnected = false
                Log.d("Power", "Connected")
                val str = "MainReceiver"

                val pm =  context?.getSystemService(Context.POWER_SERVICE) as PowerManager
                val wakeLock: WakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP),
                        "MainReceiver"
                )
                wakeLock.acquire(1000L);

                val job = GlobalScope.launch(Dispatchers.Main) {

                    delay(3000L)
                    Log.d("Power", "ты в корутине")
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                    pendingIntent.send()
                }
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d("Power", "Disconnected")
                val i = Intent(context, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                i.putExtra("close_activity", true)
                context!!.startActivity(i)
                disconnected = true
            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.d("Power", "Off")
                if (!disconnected){
                    val str = "MainReceiver"
                    val pm =  context?.getSystemService(Context.POWER_SERVICE) as PowerManager
                    val wakeLock: WakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                            or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP), str )
                    wakeLock.acquire(1000L);

                    val job = GlobalScope.launch(Dispatchers.Main) {
                        delay(1000L)
                        Log.d("Power", "ты в корутине")
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                        val pendingIntent = PendingIntent.getActivity(context,
                                0, intent, 0)
                        pendingIntent.send()
                    }
                }
            }
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("Power", "Boot completed!")
                ContextCompat.startForegroundService(context!!,
                        Intent(context, MyService::class.java))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                val pendingIntent = PendingIntent.getActivity(context, 0,
                        Intent(context, MainActivity::class.java), 0)
                pendingIntent.send()
            }
        }
    }
}