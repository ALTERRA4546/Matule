package com.example.matule

import android.content.Intent
import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        //onTick()

        var screen = findViewById<ImageView>(R.id.imageView2)
        screen.alpha = 0f
        screen.animate().setDuration(1000).alpha(1f).withEndAction()
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    /*fun onTick() {
        object : CountDownTimer(3000,1000) {
            override fun onTick(mllisUnitFinished: Long) {
                TODO("Not yet implemented")
            }

            override fun onFinish() {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                finish()
            }
        }.start()
    }*/
}