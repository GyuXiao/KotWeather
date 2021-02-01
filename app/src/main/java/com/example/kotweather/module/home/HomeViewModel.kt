package com.example.kotweather.module.home

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.Place
import com.example.kotweather.module.main.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel (application: Application) : BaseViewModel<HomeRepository>(application) {
    val mFirstPlaceData: MutableLiveData<Place> = MutableLiveData()
    fun queryFirstPlace() {
        viewModelScope.launch {
            mFirstPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryFirstPlace()
            }
        }
    }
}