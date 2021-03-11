package com.example.fullscreenshowcase

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.os.BatteryManager.BATTERY_PLUGGED_AC
import android.os.BatteryManager.BATTERY_PLUGGED_USB
import android.util.Log


class PowerConnectionReceiver: BroadcastReceiver() {

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
            }
            Intent.ACTION_POWER_DISCONNECTED -> {
                Log.d("Power", "Disconnected")
            }
        }

        Log.d("Power", "$isCharging $usbCharge $acCharge ${intent.action}")
    }
}