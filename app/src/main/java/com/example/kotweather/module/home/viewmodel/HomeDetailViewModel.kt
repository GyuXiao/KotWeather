package com.example.kotweather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.initiateRequest
import com.example.kotweather.model.*
import com.example.kotweather.module.home.repository.HomeDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeDetailViewModel(application: Application):
    BaseViewModel<HomeDetailRepository>(application) {

//    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
//    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val mRealtimeData: MutableLiveData<RealTime> = MutableLiveData()
    val mDailyData: MutableLiveData<Daily> = MutableLiveData()
    val mHourlyData: MutableLiveData<HourlyData> = MutableLiveData()

//    fun queryFirstPlace(){
//        viewModelScope.launch {
//            mFirstPlaceData.value = withContext(Dispatchers.IO){
//                mRepository.queryFirstPlace()
//            }
//        }
//    }
//
//    fun queryAllPlace(){
//        viewModelScope.launch {
//            mPlaceData.value = withContext(Dispatchers.IO){
//                mRepository.queryAllPlace()
//            }
//        }
//    }

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

    fun loadHourlyWeather(lng: String?, lat: String?) {
        initiateRequest({
            mHourlyData.value = mRepository.loadHourlyWeather(lng, lat)
        }, loadState)
    }


    fun updateChoosePlace(temperature: Int, skycon: String, name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.updateChoosePlace(temperature, skycon, name)
            }
        }
    }
}