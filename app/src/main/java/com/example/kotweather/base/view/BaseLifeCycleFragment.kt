package com.example.kotweather.base.view

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.callback.EmptyCallback
import com.example.kotweather.common.callback.ErrorCallback
import com.example.kotweather.common.callback.LoadingCallback
import com.example.kotweather.common.getAppViewModel
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.example.kotweather.module.app.AppViewModel
import com.kingja.loadsir.callback.SuccessCallback

abstract class BaseLifeCycleFragment<VM : BaseViewModel<*>, DB: ViewDataBinding> :
        BaseFragment<VM, DB>() {

    val appViewModel: AppViewModel by lazy { getAppViewModel() }


    override fun initView() {
        showLoading()
        mViewModel.loadState.observe(this, observer)
        initDataObserver()
    }

    open fun initDataObserver() {}


    private val observer by lazy {
        Observer<State> {
            it?.let {
                when (it.code) {
                    StateType.SUCCESS -> showSuccess()
                    StateType.EMPTY -> showEmpty()
                    StateType.ERROR -> showError("异常")
                    StateType.NETWORK_ERROR -> showError("网络异常")
                    StateType.LOADING -> showLoading()
                }
            }
        }
    }

    open fun showSuccess() {
        loadService.showCallback(SuccessCallback::class.java)
    }

    open fun showError(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
        loadService.showCallback(ErrorCallback::class.java)
    }

    open fun showEmpty() {
        loadService.showCallback(EmptyCallback::class.java)
    }

    open fun showLoading() {
        loadService.showCallback(LoadingCallback::class.java)
    }

    override fun reLoad() {
        showLoading()
        initData()
        initDataObserver()
        super.reLoad()
    }

}