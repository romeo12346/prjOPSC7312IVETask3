package com.example.prjopsc7312ivetask3

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager : LocationManager
    private val locationPermissionCode  = 2
    private  lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.btnLocation)

        locationAdapter = LocationAdapter()

        button.setOnClickListener(){
            getLocation()
        }

    }
    private fun getLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if((ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION) , locationPermissionCode)
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)


    }

    override fun onLocationChanged(location: Location) {

        val view: RecyclerView = findViewById(R.id.recyclerView)
        val geocoder = Geocoder(this , Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude , location.longitude, 1)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
        val formatted = current.format(formatter)

        val gsonPretty = GsonBuilder().setPrettyPrinting().create()

        val details = Location(location.latitude.toString() , location.longitude.toString() ,  formatted.toString() , addresses?.get(0)?.getAddressLine(0).toString() )
        val json = gsonPretty.toJson(details)
        val detailsList = Gson().fromJson( "["+ json + "]" , Array<com.example.prjopsc7312ivetask3.Location>::class.java).toList()

        view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = locationAdapter
        }

        locationAdapter.submitList(detailsList)

        Log.d(TAG, json)


    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == locationPermissionCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this , "Permission Granted" , Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this , "Permission Denied" , Toast.LENGTH_SHORT).show()
            }
        }
    }
}