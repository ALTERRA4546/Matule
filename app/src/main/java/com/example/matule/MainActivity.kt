package com.example.matule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.LinkedList
import java.util.Queue

class MainActivity : AppCompatActivity() {
    private lateinit var helloScreenImage: ImageView
    private lateinit var viewBarImage: ImageView
    private lateinit var welcomeTextTextView: TextView
    private lateinit var headTextTextView: TextView
    private lateinit var mainTextTextView: TextView
    private lateinit var nextButton: Button

    private var countClick = 0;

    private val helloQueue: Queue<helloScreanQueue> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helloQueue.add(helloScreanQueue(R.drawable.hello_screen_2_1, R.drawable.hello_screen_2_2, "Начнем путешествие", "Умная, великолепная и модная коллекция Изучите сейчас", "Далее"))
        helloQueue.add(helloScreanQueue(R.drawable.hello_screen_3_1, R.drawable.hello_screen_3_2, "У вас есть сила, чтобы", "В вашей комнате много красивых и привлекательных растений", "Далее"))

        helloScreenImage = findViewById<ImageView>(R.id.helloScreen)
        viewBarImage = findViewById<ImageView>(R.id.viewBar)
        welcomeTextTextView = findViewById<TextView>(R.id.welcomeText)
        headTextTextView = findViewById<TextView>(R.id.headText)
        mainTextTextView = findViewById<TextView>(R.id.mainText)
        nextButton = findViewById<Button>(R.id.beginHelloScreenButton)
    }

    /*fun nextPageOpen(view: View)
    {
        startActivity(Intent(this, SingIn::class.java))
        finish()
    }*/

    fun nextScreenOnClick(view:View)
    {
        if(helloQueue.isEmpty()) {
            startActivity(Intent(this, SingIn::class.java))
            finish()
        }
        else {
            val nextPool = helloQueue.poll()

            helloScreenImage.setImageResource(nextPool.image)
            viewBarImage.setImageResource(nextPool.barImage)
            headTextTextView.text = nextPool.title
            mainTextTextView.text = nextPool.text
            nextButton.text = nextPool.buttonText

            if (countClick == 0) {
                welcomeTextTextView.visibility = View.GONE
                headTextTextView.visibility = View.VISIBLE
                mainTextTextView.visibility = View.VISIBLE
            }

            countClick++;
        }
    }

    data class helloScreanQueue (
        val image: Int,
        val barImage: Int,
        val title: String,
        val text: String,
        val buttonText: String
    )
}