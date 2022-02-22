package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityMapBinding
import com.example.myapplication.model.ATM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val place = LatLng(52.4345, 30.9754)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 11.5f))
        RetrofitInstance.getATM().enqueue(object : Callback<List<ATM>> {
            override fun onResponse(call: Call<List<ATM>>?, response: Response<List<ATM>>?) {
                if (response?.isSuccessful == true && response.body() != null) {
                    val list = response.body()!!
                    list.forEach {
                        val point = LatLng(it.gps_x, it.gps_y)
                        map.addMarker(MarkerOptions().position(point).title("Банкомат"))
                    }
                }
            }
            override fun onFailure(call: Call<List<ATM>>?, t: Throwable?) {

            }
        })
    }
}