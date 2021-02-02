package com.example.kotweather.module.main

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.Place

class AppViewModel(application: Application): BaseViewModel<MainRepository>(application) {
    var currectPlace = MutableLiveData<Place>()

    fun changeCurrentPlace(place: Place) {
        currectPlace.value = place
    }
}