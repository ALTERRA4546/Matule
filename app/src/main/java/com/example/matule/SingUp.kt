package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SingUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun singUpClick(view: View)
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    fun singInClick(view:View)
    {
        startActivity(Intent(this, SingIn::class.java))
        finish()
    }

    fun goBackClick(view:View)
    {
        startActivity(Intent(this, SingIn::class.java))
        finish()
    }
}