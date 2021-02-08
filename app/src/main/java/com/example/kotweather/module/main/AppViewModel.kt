package com.example.kotweather.module.main

import com.example.kotweather.base.callback.UnPeekLiveData
import com.example.kotweather.base.viewmodel.BaseViewModel


class AppViewModel: BaseViewModel<AppRepository>() {
    var currentPlace = UnPeekLiveData<Int>()

    fun changeCurrentPlace(position: Int) {
        currentPlace.value = position
    }

}