package com.example.kotweather.module.main

import android.app.Application
import com.example.kotweather.base.callback.UnPeekLiveData
import com.example.kotweather.base.viewmodel.BaseViewModel


class AppViewModel(application: Application): BaseViewModel<AppRepository>(application) {
    var currentPlace = UnPeekLiveData<Int>()

    fun changeCurrentPlace(position: Int) {
        currentPlace.value = position
    }

}