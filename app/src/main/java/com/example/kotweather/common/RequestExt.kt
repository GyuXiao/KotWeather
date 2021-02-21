package com.example.kotweather.common

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kotweather.base.repository.BaseRepository
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.state.State
import com.example.kotweather.network.NetExceptionHandle
import kotlinx.coroutines.launch

/**
 * 这里使用ViewModelScope的原因：
 * 当在ViewModel中引入协程，如果直接使用CoroutineScope，那么需要手动在onClear方法中取消协程，如果忘记取消协程会导致内存泄漏
 * 此时需要使用ViewModel扩展属性ViewModelScope来实现协程作用域
 */
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