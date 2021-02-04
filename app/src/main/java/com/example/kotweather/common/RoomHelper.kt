package com.example.kotweather.common

import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.example.kotweather.model.Place
import com.example.kotweather.module.chooseplace.model.database.PlaceDataBase

/**
 * Room使用第四步
 * 这里相当于Service层，主要是处理数据库的逻辑
 */
object RoomHelper {
    // 第一步，获取数据库实例
    private val placeDataBase by lazy {
        PlaceDataBase.getInstance(BaseApplication.instance)
    }

    // 第二步，获取Dao层的访问实例
    private val placeDao by lazy {
        placeDataBase?.placeDao()
    }

    suspend fun queryAllPlaces(loadState: MutableLiveData<State>): MutableList<Place> {
        val response = placeDao?.queryAllPlace()?.toMutableList()
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
                var i = placeDao!!.deleteArticle(it)
            }
            it.insertPlace(place)
        }
    }

    suspend fun deletePlace(place: Place) {
        placeDao?.deleteArticle(place)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }
}