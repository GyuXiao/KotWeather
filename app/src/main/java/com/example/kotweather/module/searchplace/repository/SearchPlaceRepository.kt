package com.example.kotweather.module.searchplace.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import com.example.kotweather.module.searchplace.model.SearchPlaceResponse

/**
 * 搜索城市页面的仓库层，这里有两个作用，一是发起网络请求，二是将数据插入数据库
 */
class SearchPlaceRepository(var loadState: MutableLiveData<State>) : ApiRepository() {

    suspend fun searchPlaces(query: String): SearchPlaceResponse {
        return apiService.searchPlaces(query)
    }

    suspend fun loadRealtimeWeather(lng: String?, lat: String?) = apiService.loadRealtimeWeather(lng, lat)

    suspend fun insertPlaces(place: Place): Long? = RoomHelper.insertPlace(place)

    suspend fun insertChoosePlace(choosePlaceData: ChoosePlaceData): Long? = RoomHelper.insertChoosePlace(choosePlaceData)
}