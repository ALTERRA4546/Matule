package com.example.matule

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SingUp : AppCompatActivity() {
    lateinit var userName : TextView
    lateinit var email : TextView
    lateinit var password : TextView
    lateinit var personalData : CheckBox
    lateinit var singUpButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sing_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userName = findViewById<TextView>(R.id.nameSingUp)
        email = findViewById<TextView>(R.id.emailSingUp)
        password = findViewById<TextView>(R.id.passwordSingUp)
        personalData = findViewById<CheckBox>(R.id.personalDataSingUpCheck)
        singUpButton = findViewById<Button>(R.id.singUpButton)

        personalData.setOnCheckedChangeListener {
            _, isChecked -> personalData.isEnabled = isChecked

            if (personalData.isChecked)
            {
                singUpButton.setBackgroundColor(Color.parseColor("#1897E0"))
            }
            else
            {
                singUpButton.setBackgroundColor(Color.GRAY)
            }
        }

        singUpButton.setBackgroundColor(Color.GRAY)
        singUpButton.isEnabled = false
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

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun singUpClick(view: View)
    {
        if(!isValidEmail(email.text.toString()))
        {
            Toast.makeText(applicationContext, "Формат почты неверный", Toast.LENGTH_SHORT).show()
            return
        }

        if(!personalData.isChecked)
        {
            Toast.makeText(applicationContext, "Вы не дали согласие на обработку персональных данных", Toast.LENGTH_SHORT).show()
            return
        }

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