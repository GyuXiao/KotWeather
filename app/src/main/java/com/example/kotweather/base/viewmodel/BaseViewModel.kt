package com.example.kotweather.base.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotweather.base.repository.BaseRepository
import com.example.kotweather.common.state.State
import com.example.kotweather.common.util.CommonUtil

open class BaseViewModel<T : BaseRepository>: ViewModel(){
    val loadState by lazy {
        MutableLiveData<State>()
    }

    val mRepository: T by lazy {
        (CommonUtil.getClass<T>(this))
                .getDeclaredConstructor(MutableLiveData::class.java)
                .newInstance(loadState)
    }

    override fun onCleared() {
        super.onCleared()
        mRepository.unSubscribe()
    }

}