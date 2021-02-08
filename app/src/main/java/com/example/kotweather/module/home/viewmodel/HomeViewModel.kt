package com.example.kotweather.module.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.Place
import com.example.kotweather.module.home.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: BaseViewModel<HomeRepository>() {
    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }

    }
}