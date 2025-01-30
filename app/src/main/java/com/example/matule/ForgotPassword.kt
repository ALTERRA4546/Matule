package com.example.matule

import android.accounts.Account
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class ForgotPassword : AppCompatActivity() {
    lateinit var email : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        email = findViewById<TextView>(R.id.emailForgot)
    }

    fun forgotPasswordClick(view: View)
    {
        /*var builder = AlertDialog.Builder(this);
        builder.setTitle("Вход")
            .setMessage("Верный логин и пароль")
            .setCancelable(true)
            .setPositiveButton("Да"){
                _,_ -> Toast.makeText(this,"Успех", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Нет"){
                _,_ -> Toast.makeText(this,"Плохо", Toast.LENGTH_LONG).show()
            }

        val dialog = builder.create()
        dialog.show()*/



        if(!isValidEmail(email.text.toString())) {
            Toast.makeText(applicationContext, "Формат почты неверный", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            var supabase = SupabaseManager()

            var result = supabase.forgotPassword(email.text.toString())

            if(result.isSuccess) {
                val layout =
                    LayoutInflater.from(this@ForgotPassword).inflate(R.layout.check_your_mail, null)
                val builder = AlertDialog.Builder(this@ForgotPassword).setView(layout)
                val show = builder.show()
                show.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                val mainLayout = layout.findViewById<LinearLayout>(R.id.mailLayoutCheckYourMail)

                mainLayout.setOnClickListener {
                    show.dismiss()

                    val intent = Intent(this@ForgotPassword, OTPCheck::class.java)
                    intent.putExtra("account_email", email.text.toString())
                    this@ForgotPassword.startActivity(intent)
                }
            }
            else
            {
                Toast.makeText(this@ForgotPassword, "Ошибка отправки почты", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    fun goBackClick(view:View)
    {
        //onBackPressed()
        startActivity(Intent(this, SingIn::class.java))
        finish()
    }
}