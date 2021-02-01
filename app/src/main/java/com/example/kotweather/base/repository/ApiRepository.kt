package com.example.kotweather.base.repository

import com.example.kotweather.network.ApiService
import com.example.kotweather.network.RetrofitFactory

abstract class ApiRepository: BaseRepository() {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.createRetrofit(ApiService::class.java)
    }
}