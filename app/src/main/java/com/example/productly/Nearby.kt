package com.example.productly

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_nearby.*
import org.json.JSONException
import org.json.JSONObject

class Nearby : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var gMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nearby)

        supportActionBar?.title = "Productly Nearby"

        var actionBar = getSupportActionBar()

        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Productly COVID-19 Alert")
        builder.setMessage("Due to the current pandemic, everyone is encouraged to stay at home and keep a safe distance to prevent the spread of the virus. Please wear a face mask and head out at your own risk.")
        builder.setNeutralButton("OK", { dialogInterface: DialogInterface, i: Int ->})
        builder.show()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.frNearbyMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->

                val myLat = location!!.latitude
                val myLong = location!!.longitude
                CurrLocation.lat = myLat
                CurrLocation.lng = myLong

                mapMarkers(CurrLocation.lat,CurrLocation.lng,"You are here")

            }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onMapReady(goMap: GoogleMap) {
        gMap=goMap
    }

    fun mapMarkers(myLatVal:Double, myLongVal:Double, myTitle:String){

        val myLocation = LatLng(myLatVal,myLongVal)

        gMap.addMarker(
            MarkerOptions()
                .position(myLocation)
                .title(myTitle))
        gMap.setMinZoomPreference(15.0F);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
    }

    fun btnCafeClick(view: View) {
        gMap.clear()

        val queueCafe = Volley.newRequestQueue(this)
        val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ CurrLocation.lat + "," +
                CurrLocation.lng + "&radius=1500&type=cafe&key=AIzaSyDosir5QHk1yL6e7a0Xv3RdtGeL53ZcUOM"

        val postRequestCafe = StringRequest(
            Request.Method.GET, url,

            Response.Listener<String> { response ->
                try {
                    val objectNearby = JSONObject(response)
                    val objResults = objectNearby.getJSONArray("results")

                    for (i in 0 until objResults.length()){
                        val cafePlaces = objResults.getJSONObject(i)
                        val myLat = cafePlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                        val myLong = cafePlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                        val myTitle = cafePlaces.getString("name")
                        mapMarkers(myLat,myLong,myTitle)
                    }

                }
                catch (e: JSONException){
                    Snackbar.make(snackbar_nearby, e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Snackbar.make(snackbar_nearby, error.message.toString(), Snackbar.LENGTH_LONG).show()
            })
        queueCafe.add(postRequestCafe)
    }
    fun btnParkClick(view: View) {
        gMap.clear()

        val queuePark = Volley.newRequestQueue(this)
        val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ CurrLocation.lat + "," +
                CurrLocation.lng + "&radius=1500&type=park&key=AIzaSyDosir5QHk1yL6e7a0Xv3RdtGeL53ZcUOM"

        val postRequestPark = StringRequest(
            Request.Method.GET, url,

            Response.Listener<String> { response ->
                try {
                    val objectNearby = JSONObject(response)
                    val objResults = objectNearby.getJSONArray("results")

                    for (i in 0 until objResults.length()){
                        val parkPlaces = objResults.getJSONObject(i)
                        val myLat = parkPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                        val myLong = parkPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                        val myTitle = parkPlaces.getString("name")
                        mapMarkers(myLat,myLong,myTitle)
                    }

                }
                catch (e: JSONException){
                    Snackbar.make(snackbar_nearby, e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Snackbar.make(snackbar_nearby, error.message.toString(), Snackbar.LENGTH_LONG).show()
            })
        queuePark.add(postRequestPark)

    }

    fun btnSupermarketClick(view: View) {
        gMap.clear()

        val queueSupermarket = Volley.newRequestQueue(this)
        val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ CurrLocation.lat + "," +
                CurrLocation.lng + "&radius=1500&type=supermarket&key=AIzaSyDosir5QHk1yL6e7a0Xv3RdtGeL53ZcUOM"

        val postMarket = StringRequest(
            Request.Method.GET, url,

            Response.Listener<String> { response ->
                try {
                    val objectNearby = JSONObject(response)
                    val objResults = objectNearby.getJSONArray("results")

                    for (i in 0 until objResults.length()){
                        val marketPlaces = objResults.getJSONObject(i)
                        val myLat = marketPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                        val myLong = marketPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                        val myTitle = marketPlaces.getString("name")
                        mapMarkers(myLat,myLong,myTitle)
                    }

                }
                catch (e: JSONException){
                    Snackbar.make(snackbar_nearby, e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Snackbar.make(snackbar_nearby, error.message.toString(), Snackbar.LENGTH_LONG).show()
            })
        queueSupermarket.add(postMarket)

    }

    fun btnGymClick(view: View) {
        gMap.clear()

        val queueGym = Volley.newRequestQueue(this)
        val url ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+ CurrLocation.lat + "," +
                CurrLocation.lng + "&radius=1500&type=gym&key=AIzaSyDosir5QHk1yL6e7a0Xv3RdtGeL53ZcUOM"

        val postGym = StringRequest(
            Request.Method.GET, url,

            Response.Listener<String> { response ->
                try {
                    val objectNearby = JSONObject(response)
                    val objResults = objectNearby.getJSONArray("results")

                    for (i in 0 until objResults.length()){
                        val gymPlaces = objResults.getJSONObject(i)
                        val myLat = gymPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lat")
                        val myLong = gymPlaces.getJSONObject("geometry").getJSONObject("location").getDouble("lng")
                        val myTitle = gymPlaces.getString("name")
                        mapMarkers(myLat,myLong,myTitle)
                    }

                }
                catch (e: JSONException){
                    Snackbar.make(snackbar_nearby, e.message.toString(), Snackbar.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener { error ->
                Snackbar.make(snackbar_nearby, error.message.toString(), Snackbar.LENGTH_LONG).show()
            })
        queueGym.add(postGym)
    }


}

class CurrLocation {
    companion object {
        var lat: Double = 0.0
        var lng: Double = 0.0
    }
}