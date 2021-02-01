package com.example.kotweather.module.addedplace.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State
import com.example.kotweather.model.Place

class ChoosePlaceRepository(var loadState: MutableLiveData<State>): ApiRepository() {
    suspend fun queryAllPlace() = RoomHelper.queryAllPlaces(loadState)
    suspend fun deletePlace(place: Place) = RoomHelper.deletePlace(place)
    suspend fun deleteAll() = RoomHelper.deleteAll()
}