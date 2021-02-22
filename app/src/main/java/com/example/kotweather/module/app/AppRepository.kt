package com.example.kotweather.module.app

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State

class AppRepository(var loadState: MutableLiveData<State>) : ApiRepository() {

    suspend fun queryAllChoosePlace() = RoomHelper.queryAllChoosePlace(loadState)

}