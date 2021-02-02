package com.example.kotweather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.initiateRequest
import com.example.kotweather.model.DailyResponse
import com.example.kotweather.model.Place
import com.example.kotweather.model.RealtimeResponse
import com.example.kotweather.module.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application): BaseViewModel<HomeRepository>(application) {

    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
    val mRealtimeData: MutableLiveData<RealtimeResponse> = MutableLiveData()
    val mDailyData: MutableLiveData<DailyResponse> = MutableLiveData()

    fun queryFirstPlace(){
        viewModelScope.launch {
            mFirstPlaceData.value = withContext(Dispatchers.IO){
                mRepository.queryFirstPlace()
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?){
        initiateRequest({
            mRealtimeData.value = mRepository.loadRealtimeWeather(lng, lat)
        }, loadState)
    }

    fun loadDailyWeather(lng: String?, lat: String?){
        initiateRequest({
            mDailyData.value = mRepository.loadDailyWeather(lng, lat)
        }, loadState)
    }
}