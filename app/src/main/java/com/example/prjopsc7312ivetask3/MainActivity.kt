package com.example.prjopsc7312ivetask3

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.util.Locale

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager : LocationManager
    private lateinit var tvOutput : TextView
    private  lateinit var tvAddress: TextView
    private val locationPermissionCode  = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.btnLocation)

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


        val geocoder = Geocoder(this , Locale.getDefault())
        val addresses = geocoder.getFromLocation(location.latitude , location.longitude, 1)

        tvAddress.text = addresses?.get(0)?.getAddressLine(0)

        Log.d(TAG, addresses?.get(0)?.getAddressLine(0).toString())

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