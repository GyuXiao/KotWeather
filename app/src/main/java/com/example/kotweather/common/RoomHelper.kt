package com.example.kotweather.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import com.example.kotweather.module.chooseplace.model.database.ChoosePlaceDataBase
import com.example.kotweather.module.searchplace.model.database.PlaceDataBase

/**
 * Room使用第四步
 * 这里相当于Service层，主要是处理数据库的逻辑
 */
object RoomHelper {
    // 第一步，获取数据库实例
    private val placeDataBase by lazy {
        PlaceDataBase.getInstance(BaseApplication.instance)
    }

    // 第二步，通过数据库实例获取Dao层的访问实例
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

    suspend fun queryPlaceByName(name: String): Place? {
        val response = placeDao?.queryPlaceByName(name)
        return response
    }

    suspend fun insertPlace(place: Place):Long? =
        placeDao?.let {
            it.queryPlaceByName(place.name)?.let {
                var i = placeDao!!.deletePlace(it)
            }
            it.insertPlace(place)
        }


    suspend fun deletePlace(place: Place?) {
        placeDao?.deletePlace(place!!)
    }

    suspend fun deleteAll() {
        placeDao?.deleteAll()
    }

    private val choosePlaceDataBase by lazy {
        ChoosePlaceDataBase.getInstance(BaseApplication.instance)
    }

    private val choosePlaceDao by lazy {
        choosePlaceDataBase?.choosePlaceDao()
    }

    suspend fun queryAllChoosePlace(loadState: MutableLiveData<State>): MutableList<ChoosePlaceData> {
        val response = choosePlaceDao?.queryAllPlace()?.toMutableList()
        if (response!!.isEmpty()) {
            loadState.postValue(State(StateType.SUCCESS))
        }
        return response
    }

    suspend fun insertChoosePlace(choosePlaceData: ChoosePlaceData): Long? =
        choosePlaceDao?.let {
            it.queryChoosePlaceByName(choosePlaceData.name)?.let {
                var i = choosePlaceDao!!.deleteChoosePlace(it)
                Log.d("insert", i.toString())
            }
            it.insertPlace(choosePlaceData)
        }


    suspend fun updateChoosePlace(temperature: Int, skycon: String, name: String) {
        choosePlaceDao?.let {
            it.updateChoosePlace(temperature, skycon, name)
        }
    }

    suspend fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) {
        choosePlaceDao?.deleteChoosePlace(choosePlaceData)
    }
}