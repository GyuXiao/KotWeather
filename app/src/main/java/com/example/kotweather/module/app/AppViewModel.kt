package com.example.kotweather.module.app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.callback.UnPeekLiveData
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.module.app.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AppViewModel: BaseViewModel<AppRepository>() {
    var mCurrentPlace = UnPeekLiveData<Int>()
    var mChoosePlaceData: UnPeekLiveData<MutableList<ChoosePlaceData>> = UnPeekLiveData()

    fun changeCurrentPlace(position: Int) {
        mCurrentPlace.value = position
    }

    fun queryAllChoosePlace() {
        viewModelScope.launch {
            mChoosePlaceData.value = withContext(Dispatchers.IO) {
                mRepository.queryAllChoosePlace()
            }
        }
    }
}