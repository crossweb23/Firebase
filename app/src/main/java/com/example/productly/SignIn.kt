package com.example.productly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()
    }

    fun btnStart(view: View) {

        var intent = Intent(this@SignIn, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}