package com.example.kotweather.common

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.example.kotweather.model.Place
import com.example.kotweather.module.addedplace.model.database.PlaceDataBase

object RoomHelper {
    private val placeDataBase by lazy {
        PlaceDataBase.getInstance(BaseApplication.instance)
    }

    private val placeDao by lazy {
        placeDataBase?.placeDao()
    }

    suspend fun queryAllPlaces(loadState: MutableLiveData<State>): MutableList<Place> {
        val response = placeDao?.queryAllPlace()?.reversed()?.toMutableList()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.SUCCESS))
        }
        return response
    }

    suspend fun queryFirstPlace(loadState: MutableLiveData<State>): Place? {
        val response = placeDao?.queryFirstPlace()
        return response
    }

    suspend fun insertPlace(place: Place) {
        placeDao?.let {
            it.queryPlaceByName(place.name)?.let {
                var i = placeDao!!.deleteArticle(place)
            }
            it.insertPlace(place.apply { primaryKey = 0 })
        }
    }

    suspend fun deletePlace(place: Place) {
        placeDao?.deleteArticle(place)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }
}