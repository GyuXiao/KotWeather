package com.example.kotweather.base.view

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.Utils
import com.example.kotweather.common.callback.*
import com.example.kotweather.common.getAppViewModel
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.example.kotweather.module.main.AppViewModel
import com.kingja.loadsir.callback.SuccessCallback

abstract class BaseLifeCycleActivity<VM : BaseViewModel<*>, DB: ViewDataBinding>
    : BaseActivity<VM, DB>() {

    val appViewModel: AppViewModel by lazy { getAppViewModel() }

    override fun initView() {
        showLoading()
        showSuccess()
        mViewModel.loadState.observe(this, observer)
        initDataObserver()
    }

    open fun initDataObserver() {}

    private fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    private fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(ErrorCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallback::class.java)
    }

    open fun showLoading() {
        loadService.showCallback(LoadingCallback::class.java)
    }

    /**
     * 分发应用的状态
     */
    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.EMPTY -> showEmpty()
                    StateType.ERROR -> showError("出现问题")
                    StateType.NETWORK_ERROR -> showError("网络出现问题")
                    StateType.LOADING -> showLoading()
                }
            }
        }
    }
}