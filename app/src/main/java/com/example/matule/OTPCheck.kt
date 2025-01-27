package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class OTPCheck : AppCompatActivity() {
    lateinit var  countDownTimer: CountDownTimer
    lateinit var otpver1 : TextView
    lateinit var otpver2 : TextView
    lateinit var otpver3 : TextView
    lateinit var otpver4 : TextView
    lateinit var otpver5 : TextView
    lateinit var otpver6 : TextView
    lateinit var resendLink : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otpcheck)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        resendLink = findViewById<TextView>(R.id.resendTimeOut)

        countDownTimer = object : CountDownTimer(30000, 1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                resendLink.setTextColor(ContextCompat.getColor(this@OTPCheck, R.color.black))
                val seconds = millisUntilFinished/1000
                if(seconds > 9)
                    resendLink.text = "00:$seconds"
                else
                    resendLink.text = "00:0$seconds"
            }

            override fun onFinish() {
                resendLink.text = "Отправить"
                resendLink.setTextColor(ContextCompat.getColor(this@OTPCheck, R.color.black))
                countDownTimer.cancel()
            }
        }
        countDownTimer.start()

        otpver1 = findViewById<TextView>(R.id.otpPass1)
        otpver2 = findViewById<TextView>(R.id.otpPass2)
        otpver3 = findViewById<TextView>(R.id.otpPass3)
        otpver4 = findViewById<TextView>(R.id.otpPass4)
        otpver5 = findViewById<TextView>(R.id.otpPass5)
        otpver6 = findViewById<TextView>(R.id.otpPass6)

        otpver1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    otpver2.requestFocus()
            }
        })
        otpver2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    otpver3.requestFocus()
            }
        })
        otpver3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    otpver4.requestFocus()
            }
        })
        otpver4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    otpver5.requestFocus()
            }
        })
        otpver5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    otpver6.requestFocus()
            }
        })
        otpver6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.toString().trim().isEmpty())
                    setNewPassword()
            }
        })
    }

    fun resendOtpCode(view:View)
    {
        if(resendLink.text.toString() == "Отправить")
        countDownTimer.start()
        Toast.makeText(applicationContext, "Код выслан повторно, проверьте совю почту", Toast.LENGTH_SHORT).show()
    }

    fun setNewPassword()
    {
        if(otpver1.text.isNotEmpty() && otpver2.text.isNotEmpty() && otpver3.text.isNotEmpty() && otpver4.text.isNotEmpty() && otpver5.text.isNotEmpty() && otpver6.text.isNotEmpty())  {
            var otpFull = otpver1.text.toString()+otpver2.text.toString()+otpver3.text.toString()+otpver4.text.toString()+otpver5.text.toString()+otpver6.text.toString()

            lifecycleScope.launch {
                var supabase = SupabaseManager()
                var email = intent.getStringExtra("account_email") ?: ""

                var resultOtp = supabase.otpSingIn(email, otpFull)

                if (resultOtp.isSuccess) {
                    var resultReset = supabase.resetPassword("1234")

                    if (resultReset.isSuccess) {
                        startActivity(Intent(this@OTPCheck, Home::class.java))
                        finish()
                    }
                }
                else {
                    Toast.makeText(this@OTPCheck, "Ошибка отправки кода", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun goBackClick(view: View)
    {
        startActivity(Intent(this, SingUp::class.java))
        finish()
    }
}