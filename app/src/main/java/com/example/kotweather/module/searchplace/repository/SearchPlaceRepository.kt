package com.example.kotweather.module.searchplace.repository

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.repository.ApiRepository
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.state.State
import com.example.kotweather.model.Place
import com.example.kotweather.module.searchplace.model.SearchPlaceResponse

class SearchPlaceRepository(var loadState: MutableLiveData<State>) : ApiRepository() {

    suspend fun searchPlaces(query: String): SearchPlaceResponse {
        return apiService.searchPlaces(query)
    }

    suspend fun insertPlaces(place: Place) = RoomHelper.insertPlace(place)
}