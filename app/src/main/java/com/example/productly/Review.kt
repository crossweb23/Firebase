package com.example.productly

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_review.*

class Review : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        supportActionBar?.title = "Productly App Review"

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        webViewSetup()

    }

    override fun onSupportNavigateUp(): Boolean {
        var intent = Intent(this@Review, HomeScreen::class.java)
        startActivity(intent)
        finish()
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup() {
        wb_webView.webViewClient = WebViewClient()

        wb_webView.apply {
            loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSfXEIYHLOwDsiFJEv6UquPexUqN8W6Fl8nAo7LjA9SUtyacFQ/viewform")
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true

        }
    }
}