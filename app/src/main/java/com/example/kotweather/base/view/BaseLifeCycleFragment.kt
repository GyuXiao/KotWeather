package com.example.kotweather.base.view

import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotweather.base.viewmodel.BaseViewModel
import com.example.kotweather.common.Utils
import com.example.kotweather.common.callback.EmptyCallback
import com.example.kotweather.common.callback.ErrorCallback
import com.example.kotweather.common.callback.LoadingCallback
import com.example.kotweather.common.state.State
import com.example.kotweather.common.state.StateType
import com.kingja.loadsir.callback.SuccessCallback

abstract class BaseLifeCycleFragment<VM : BaseViewModel<*>, DB: ViewDataBinding> :
        BaseFragment<VM, DB>() {

    override fun initView() {
        showLoading()
        showSuccess()
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
        super.reLoad()
    }

}