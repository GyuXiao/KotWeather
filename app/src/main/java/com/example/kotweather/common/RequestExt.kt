package com.example.kotweather.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.repository.BaseRepository
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.state.State
import com.example.kotweather.network.NetExceptionHandle
import kotlinx.coroutines.launch


fun <T: BaseRepository> BaseViewModel<T>.initiateRequest(
        block: suspend () -> Unit,
        loadState: MutableLiveData<State>
){
    viewModelScope.launch {
        runCatching {
            block()
            Log.d("Gyu", "success")
        }.onSuccess {
            Log.d("Gyu", "success1")
        }.onFailure {
            Log.d("Gyu", "fail")
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}