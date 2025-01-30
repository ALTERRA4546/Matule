package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Profile : AppCompatActivity() {
    lateinit var userName: TextView
    lateinit var userMail: TextView
    lateinit var userPassword: TextView
    lateinit var userPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userName = findViewById(R.id.userNameProfileEdit)
        userMail = findViewById(R.id.userEmailProfileEdit)
        userPassword = findViewById(R.id.userPasswordProfileEdit)
        userPhoto = findViewById(R.id.userPhotoProfileEdit)

        lifecycleScope.launch {
            var supabase = SupabaseManager()

            var user = supabase.getProfile()

            userName.text = user.last_name + " " + user.first_name
            userMail.text = user.email
            userPassword.text = user.password
        }
    }

    fun saveButton (view:View) {
        lifecycleScope.launch {
            var supabase = SupabaseManager()

            supabase.updateUserProfileMain(userName.text.toString(), userMail.text.toString(), userPassword.text.toString())

            startActivity(Intent(this@Profile, ProfileMain::class.java))
            finish()
        }
    }

    fun backButton (view:View) {
        startActivity(Intent(this, ProfileMain::class.java))
        finish()
    }

    fun forgotPassword (view:View) {
        startActivity(Intent(this, ForgotPassword::class.java))
        finish()
    }
}