package com.example.kotweather.module.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppEventViewModel: ViewModel() {
    val addChoosePlace = MutableLiveData<Boolean>()
    var addPlace = MutableLiveData<Boolean>()
    val changeCurrentPlace = MutableLiveData<Boolean>()
}