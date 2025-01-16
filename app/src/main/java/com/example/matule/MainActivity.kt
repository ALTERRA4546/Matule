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

    private val helloScreenImageQueue: Queue<Int> = LinkedList()
    private val viewBarImageQueue: Queue<Int> = LinkedList()
    private val headTextQueue: Queue<String> = LinkedList()
    private val mainTextQueue: Queue<String> = LinkedList()
    private val buttonTextQueue: Queue<String> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helloScreenImageQueue.add(R.drawable.hello_screen_2_1)
        helloScreenImageQueue.add(R.drawable.hello_screen_3_1)

        viewBarImageQueue.add(R.drawable.hello_screen_2_2)
        viewBarImageQueue.add(R.drawable.hello_screen_3_2)

        headTextQueue.add("Начнем путешествие")
        headTextQueue.add("У вас есть сила, чтобы")

        mainTextQueue.add("Умная, великолепная и модная коллекция Изучите сейчас")
        mainTextQueue.add("В вашей комнате много красивых и привлекательных растений")

        buttonTextQueue.add("Далее")
        buttonTextQueue.add("Далее")

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
        if (helloScreenImageQueue.size == countClick) {
            startActivity(Intent(this, SingIn::class.java))
            finish()
        }
        else
        {
            val nextHelloScreen = helloScreenImageQueue.poll()
            val nextViewBar = viewBarImageQueue.poll()
            val nextHeadText = headTextQueue.poll()
            val nextMainText = mainTextQueue.poll()
            var nextButtonText = buttonTextQueue.poll()

            helloScreenImage.setImageResource(nextHelloScreen)
            viewBarImage.setImageResource(nextViewBar)
            headTextTextView.text = nextHeadText
            mainTextTextView.text = nextMainText
            nextButton.text = nextButtonText

            if (countClick == 0) {
                welcomeTextTextView.visibility = View.GONE
                headTextTextView.visibility = View.VISIBLE
                mainTextTextView.visibility = View.VISIBLE
            }

            helloScreenImageQueue.add(nextHelloScreen)
            viewBarImageQueue.add(nextViewBar)
            headTextQueue.add(nextHeadText)
            mainTextQueue.add(nextMainText)

            countClick++;
        }
    }
}