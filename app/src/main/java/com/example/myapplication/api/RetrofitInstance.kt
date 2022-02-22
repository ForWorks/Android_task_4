package com.example.myapplication.api

import com.example.myapplication.model.ATM
import com.example.myapplication.utils.Constants.Companion.BASE_URL
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val api by lazy {
        retrofit.create(APIService::class.java)
    }
    fun getATM(): Call<List<ATM>> {
        return api.getATM()
    }
}