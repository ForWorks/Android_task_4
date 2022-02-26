package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.controllers.Controller
import com.example.myapplication.databinding.ActivityMapBinding
import com.example.myapplication.model.ATM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapBinding
    companion object {
        private var list: List<ATM>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val point = LatLng(52.4345, 30.9754)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 11.5f))

        val controller = Controller()

        if(!controller.isOnline(this) && list == null)
            Snackbar.make(binding.root, "Check your internet connection", Snackbar.LENGTH_LONG)
                .setAction("Close") {}
                .show()

        CoroutineScope(Dispatchers.IO).launch {
            while (!controller.isOnline(applicationContext) && list == null)
                continue
            if(list == null)
                list = controller.getList()
            runOnUiThread {
                controller.addMarkers(list, map)
            }
        }
    }
}