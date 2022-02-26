package com.example.myapplication.controllers

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.model.ATM
import com.example.myapplication.utils.Constants
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Controller {

    fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetwork
        if (netInfo != null)
            return true
        return false
    }

    fun addMarkers(list: List<ATM>?, map: GoogleMap) {
        list?.forEach {
            map.addMarker(
                MarkerOptions()
                .position(LatLng(it.gps_x, it.gps_y))
                .title(it.address_type + " " + it.address + " " + it.house)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)))
        }
    }

    suspend fun getList(): List<ATM>? {
        return suspendCoroutine { continuation ->
            RetrofitInstance.getATM().enqueue(object : Callback<List<ATM>> {
                override fun onResponse(call: Call<List<ATM>>?, response: Response<List<ATM>>?) {
                    if (response?.isSuccessful == true && response.body() != null) {
                        continuation.resume(response.body()!!)
                    }
                }
                override fun onFailure(call: Call<List<ATM>>?, t: Throwable?) {
                    Log.e(Constants.ERROR, t?.message.toString())
                }
            })
        }
    }
}