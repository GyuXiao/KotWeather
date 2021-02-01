package com.example.kotweather.module.home

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State

class HomeRepository(var loadState: MutableLiveData<State>): ApiRepository() {
    suspend fun queryFirstPlace() = RoomHelper.queryFirstPlace(loadState)
}