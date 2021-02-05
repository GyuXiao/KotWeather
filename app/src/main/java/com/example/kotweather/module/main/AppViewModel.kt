package com.example.kotweather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AppViewModel(application: Application): BaseViewModel<MainRepository>(application) {
    var currentPlace = MutableLiveData<Place>()

    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    val mChoosePlaceData: MutableLiveData<MutableList<ChoosePlaceData>> = MutableLiveData()

    fun changeCurrentPlace(place: Place) {
        currentPlace.value = place
    }

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllPlace()
            }
        }
    }

    fun queryChoosePlace() {
        viewModelScope.launch {
            mChoosePlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllChoosePlace()
            }
        }
    }
}