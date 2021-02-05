package com.example.kotweather.module.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AppMessageViewModel(application: Application): AndroidViewModel(application) {
    val addChoosePlace = MutableLiveData<Boolean>()
    var addPlace = MutableLiveData<Boolean>()
    val changeCurrentPlace = MutableLiveData<Boolean>()
}