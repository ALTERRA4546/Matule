package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ProfileMain : AppCompatActivity() {
    lateinit var phoneRegion : Spinner
    lateinit var userName : TextView
    lateinit var userSurname : TextView
    lateinit var userAddress : TextView
    lateinit var userPhone : TextView
    lateinit var userPhoto : ImageView
    lateinit var userProfileName : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        phoneRegion = findViewById<Spinner>(R.id.phoneRegionProfile)

        userName = findViewById(R.id.nameMainProfile)
        userSurname = findViewById(R.id.surnameMainProfile)
        userAddress = findViewById(R.id.addressMainProfile)
        userPhone = findViewById(R.id.phoneMainProfile)
        userPhoto = findViewById(R.id.photoMainProfile)
        userProfileName = findViewById(R.id.profileNameMainProfile)

        val items = listOf("+7", "+1", "+4", "+234")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        phoneRegion.adapter = adapter

        lifecycleScope.launch {
            var supabaseManager = SupabaseManager()
            var user = supabaseManager.getProfile()

            userName.text = user.first_name
            userSurname.text = user.last_name
            userAddress.text = user.address
            userPhone.text = user.phone
            phoneRegion.setSelection(user.phone_region)
            userProfileName.text = user.last_name + " " + user.first_name
        }
    }

    fun doneButton (view:View) {

        lifecycleScope.launch {
            var supabase = SupabaseManager()

            supabase.updateUserProfile(userName.text.toString(), userSurname.text.toString(), userAddress.text.toString(), userPhone.text.toString(), phoneRegion.selectedItemPosition)

            startActivity(Intent(this@ProfileMain, Home::class.java))
            finish()
        }
    }

    fun changeProfile (view:View) {
        startActivity(Intent(this, Profile::class.java))
    }
}