package com.example.kotweather.module.app

import com.example.kotweather.base.callback.UnPeekLiveData
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.module.app.AppRepository


class AppViewModel: BaseViewModel<AppRepository>() {
    var currentPlace = UnPeekLiveData<Int>()

    fun changeCurrentPlace(position: Int) {
        currentPlace.value = position
    }

}