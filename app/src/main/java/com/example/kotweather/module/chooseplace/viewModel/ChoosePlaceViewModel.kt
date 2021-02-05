package com.example.kotweather.module.chooseplace.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.RoomHelper
import com.example.kotweather.common.initiateRequest
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import com.example.kotweather.model.RealTime
import com.example.kotweather.module.chooseplace.repository.ChoosePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChoosePlaceViewModel(application: Application) : BaseViewModel<ChoosePlaceRepository>(application) {

    val mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()
    val mRealTimeData: MutableLiveData<RealTime> = MutableLiveData()
    val mChoosePlaceData : MutableLiveData<MutableList<ChoosePlaceData>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO){
                mRepository.queryAllPlace() as MutableList<Place>
            }
        }
    }

    fun queryAllChoosePlace() {
        viewModelScope.launch {
            mChoosePlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllChoosePlace()
            }
        }
    }

    fun deleteChoosePlace(choosePlaceData: ChoosePlaceData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.deleteChoosePlace(choosePlaceData)
                queryAllChoosePlace()
            }
        }
    }

    fun deletePlace(name: String){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                mRepository.deletePlace(RoomHelper.queryPlaceByName(name))
                queryAllChoosePlace()
            }
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                mRepository.deleteAll()
                queryAllPlace()
            }
        }
    }

    fun loadRealtimeWeather(lng: String?, lat: String?) {
        initiateRequest(
                { mRealTimeData.value = mRepository.loadRealtimeWeather(lng, lat) },
                loadState
        )
    }
}