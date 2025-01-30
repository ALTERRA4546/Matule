package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class ProfileMain : AppCompatActivity() {
    lateinit var phoneRegion : Spinner

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

        val items = listOf("+7", "+1", "+4", "+234")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        phoneRegion.adapter = adapter

        lifecycleScope.launch {
            var supabaseManager = SupabaseManager()
            var user = supabaseManager.getProfile()
        }
    }

    fun doneButton (view:View) {
        startActivity(Intent(this, Home::class.java))
        finish()
    }

    fun changeProfile (view:View) {
        startActivity(Intent(this, Profile::class.java))
    }
}