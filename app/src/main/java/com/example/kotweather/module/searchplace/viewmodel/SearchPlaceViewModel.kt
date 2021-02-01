package com.example.kotweather.module.searchplace.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.module.searchplace.model.SearchPlaceResponse
import com.example.kotweather.module.searchplace.repository.SearchPlaceRepository
import com.example.kotweather.common.initiateRequest
import com.example.kotweather.model.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SearchPlaceViewModel(application: Application) : BaseViewModel<SearchPlaceRepository>(application) {

    val mSearchPlacesData: MutableLiveData<SearchPlaceResponse> = MutableLiveData()

    fun searchPlaces(query: String) {
        initiateRequest({
            mSearchPlacesData.value = mRepository.searchPlaces(query)
        }, loadState)
    }

    fun insertPlace(place: Place) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mRepository.insertPlaces(place)
            }
        }
    }
}