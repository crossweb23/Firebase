package com.example.productly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.productly.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_mood_tracker.*
import org.json.JSONException
import org.json.JSONObject

class MoodTracker : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mood_tracker)
        supportActionBar?.title = "Mood Tracker"

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun btnSaveOnclick(view: View) {

        val emotion = edEmotion.text.toString()
        val weather = edWeather.text.toString()
        val udone = edDone.text.toString()
        val recordsQueue = Volley.newRequestQueue(this)
        val url = "http://35.236.127.110/productly/BLL/DiaryAPI.php"
        val insertRequest = object: StringRequest(
            Request.Method.POST, url,
            Response.Listener <String> { response ->
                try {
                    val obj = JSONObject(response)
                    Snackbar.make(snackbar_actions,
                        obj.getString("message"), Snackbar.LENGTH_LONG).show()

                    val intent = Intent(applicationContext, HomeScreen::class.java)
                    startActivity(intent)

                }
                catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener{ error ->
                Snackbar.make(snackbar_actions,
                    error.message.toString(), Snackbar.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params:MutableMap<String, String> = HashMap()
                //inside the “ ” are the http variables defined in StudentAPI
                //fname,lname,mi & ylevel hold the values that you will store
                params["emotion"] = emotion
                params["weather"] = weather
                params["udone"] = udone
                return params
            }
        }
        recordsQueue.add(insertRequest)

    }


}
