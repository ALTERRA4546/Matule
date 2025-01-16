package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SingIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun restorePasswordClick(view:View)
    {
        startActivity(Intent(this, ForgotPassword::class.java))
        finish()
    }

    fun singInUserClick(view:View)
    {
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    fun createUserClick(view: View)
    {
        startActivity(Intent(this, SingUp::class.java))
        finish()
    }

    fun goBackClick(view:View)
    {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}