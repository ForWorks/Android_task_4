package com.example.myapplication.api

import com.example.myapplication.model.ATM
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("api/atm?city=Гомель")
    fun getATM(): Call<List<ATM>>
}