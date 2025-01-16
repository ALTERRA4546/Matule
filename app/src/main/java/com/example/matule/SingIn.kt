package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class SingIn : AppCompatActivity() {
    lateinit var email : TextView
    lateinit var password : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById<TextView>(R.id.emailTextView)
        password = findViewById<TextView>(R.id.passwordTextView)
    }

    fun restorePasswordClick(view:View)
    {
        startActivity(Intent(this, ForgotPassword::class.java))
        finish()
    }

    fun seePassword(view:View)
    {
        if (password.inputType == (InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        }
        else {
            password.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
    }

    fun singInUserClick(view:View)
    {
        if(!isValidEmail(email.text.toString())) {
            Toast.makeText(applicationContext, "Формат почты неверный", Toast.LENGTH_SHORT).show()
            return
        }

        if(email.text.toString() == "1@mail.ru" && password.text.toString() == "1") {
            startActivity(Intent(this, Home::class.java))
            finish()
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
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