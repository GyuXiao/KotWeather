package com.example.kotweather.module.chooseplace.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State
import com.example.kotweather.model.Place

/**
 * 选择城市页面的仓库层，直接从本地数据库查询即可
 */
class ChoosePlaceRepository(var loadState: MutableLiveData<State>): ApiRepository() {
    suspend fun queryAllPlace() = RoomHelper.queryAllPlaces(loadState)
    suspend fun deletePlace(place: Place) = RoomHelper.deletePlace(place)
    suspend fun deleteAll() = RoomHelper.deleteAll()
    suspend fun loadRealtimeWeather(lng: String?, lat: String?) =
        apiService.loadRealtimeWeather(lng, lat)
}