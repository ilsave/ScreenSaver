package com.example.fullscreenshowcase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

/*
WARNING!
        пожалуйста, обратите вниание, что при апи 30 и выше необходимо разрешить
        приложению показываться поверх других. Вот как это делается:
        1) нажать на нотификацию
        2) advanced
        3) allow display over other applications
 */

class MainActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        val prefs =
            getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)

       if (firstStart) {
           Log.d("Power", "changes")
            val intent = Intent(this, MyService::class.java)
            ContextCompat.startForegroundService(this, intent)
            val editor = prefs.edit()
            editor.putBoolean("firstStart", false)
            editor.apply()
       }

        hideSystemUI()
        videoView = findViewById(R.id.vvPlayer)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video
        val uri = Uri.parse(videoPath)
        videoView.setVideoURI(uri)
        videoView.start()



        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true
        }
        videoView.setOnTouchListener { v, event ->
            Log.d("Ilsave", "Video 1 clicked, starting playback")
            finish()
            false
        }


    }
    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra("close_activity", false)) {
            finish()
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d("Power", "config changed");
        super.onConfigurationChanged(newConfig);
        finish()
    }

}