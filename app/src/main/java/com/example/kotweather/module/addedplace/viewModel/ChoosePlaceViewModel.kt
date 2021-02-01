package com.example.kotweather.module.addedplace.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.Place
import com.example.kotweather.module.addedplace.repository.ChoosePlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChoosePlaceViewModel(application: Application) : BaseViewModel<ChoosePlaceRepository>(application) {

    var mPlaceData: MutableLiveData<MutableList<Place>> = MutableLiveData()

    fun queryAllPlace() {
        viewModelScope.launch {
            mPlaceData.value = withContext(Dispatchers.IO){
                mRepository.queryAllPlace() as MutableList<Place>
            }
        }
    }

    fun deletePlace(place: Place){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                mRepository.deletePlace(place)
                queryAllPlace()
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
}