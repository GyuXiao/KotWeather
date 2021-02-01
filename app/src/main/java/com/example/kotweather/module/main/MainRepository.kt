package com.example.kotweather.module.main

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.state.State

class MainRepository(var loadState: MutableLiveData<State>) : ApiRepository() {
}