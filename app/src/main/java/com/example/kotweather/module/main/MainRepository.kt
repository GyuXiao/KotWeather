package com.example.kotweather.module.main

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import com.example.kotweather.module.searchplace.model.SearchPlaceResponse

class MainRepository(var loadState: MutableLiveData<State>): ApiRepository() {

    suspend fun searchPlaces(query: String): SearchPlaceResponse = apiService.searchPlaces(query)

    suspend fun loadRealtimeWeather(lng: String?, lat: String?) = apiService.loadRealtimeWeather(lng, lat)

    suspend fun insertPlaces(place: Place): Long? = RoomHelper.insertPlace(place)

    suspend fun insertChoosePlaces(choosePlaceData: ChoosePlaceData): Long? =
        RoomHelper.insertChoosePlace(choosePlaceData)
}