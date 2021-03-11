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
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext


class PowerConnectionReceiver: BroadcastReceiver() {

    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onReceive(context: Context?, intent: Intent?) {
        val status = intent!!.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL

        val chargePlug = intent!!.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        val usbCharge = chargePlug == BATTERY_PLUGGED_USB
        val acCharge = chargePlug == BATTERY_PLUGGED_AC

        when(intent.action){
            Intent.ACTION_POWER_CONNECTED -> {
                Log.d("Power", "Connected")
                Toast.makeText(context, "ewqewq", Toast.LENGTH_SHORT).show()

                val intent = Intent(context, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                pendingIntent.send()
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d("Power", "Disconnected")
                Toast.makeText(context, "ewqewq", Toast.LENGTH_SHORT).show()
            }
            Intent.ACTION_SCREEN_OFF -> {
                Log.d("Power", "Off")



                val str = "MainReceiver"
                val pm =  context?.getSystemService(Context.POWER_SERVICE) as PowerManager
                val wakeLock: WakeLock = pm.newWakeLock((PowerManager.SCREEN_BRIGHT_WAKE_LOCK
                        or PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP), str )
                Toast.makeText(context, "IM ilsave", Toast.LENGTH_SHORT).show()
                wakeLock.acquire(1000L);

                val job = GlobalScope.launch(Dispatchers.Main) {

                    delay(3000L)
                    Log.d("Power", "ты в корутине")
                    val intent = Intent(context, MainActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                    pendingIntent.send()
                }


//                val myIntent = Intent(context, MainActivity::class.java)
//                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context?.startActivity(myIntent)
                Toast.makeText(context, "ewqewq", Toast.LENGTH_SHORT).show()
            }
        }

        Log.d("PowerB", "$isCharging $usbCharge $acCharge ${intent.action}")
    }
}