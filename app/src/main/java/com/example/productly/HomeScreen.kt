package com.example.productly

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.productly.databinding.ActivityHomeScreenBinding
import com.example.productly.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth


class HomeScreen : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding : ActivityHomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        val currentUser =mAuth.currentUser
        binding.WelcomeText.text = currentUser?.displayName
        toggle = ActionBarDrawerToggle(this, binding.homeScreenLayout, R.string.open, R.string.close)
        binding.homeScreenLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){

                R.id.item1 -> homeScreen()
                R.id.item2 -> urlReview()
                R.id.item3 -> signOut()
            }
            true
        }
    }

    private fun urlReview() {
        val intent = Intent(applicationContext, Review::class.java)
        startActivity(intent)

        }



    private fun homeScreen() {
        val intent = Intent(applicationContext, HomeScreen::class.java)
        startActivity(intent)



    }

    private fun signOut() {

        mAuth.signOut()
        val intent = Intent(applicationContext, SignIn::class.java)
        startActivity(intent)
        finish()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)

    }



    fun btnTodo(view: View) {

        var intent = Intent(this@HomeScreen, ToDoList::class.java)
        startActivity(intent)


    }

    fun btnTimer(view: View) {
        var intent = Intent(this@HomeScreen, Timer::class.java)
        startActivity(intent)
    }

    fun btnJournal(view: View) {
        var intent = Intent(this@HomeScreen, LockScreen::class.java)
        startActivity(intent)
    }

    fun btnNearby(view: View) {

        var intent = Intent(this@HomeScreen, Nearby::class.java)
        startActivity(intent)
    }

    fun btnMood_Tracker(view: View) {
        var intent = Intent(this@HomeScreen, MoodTracker::class.java)
        startActivity(intent)
    }

}