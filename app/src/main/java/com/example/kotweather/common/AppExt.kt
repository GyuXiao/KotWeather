package com.example.kotweather.common

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.module.main.AppViewModel
import com.example.kotweather.module.main.AppEventViewModel

//扩展函数
fun AppCompatActivity.getAppViewModel(): AppViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppViewModel::class.java)
    }
}

fun Fragment.getAppViewModel(): AppViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppViewModel::class.java)
    }
}

fun Fragment.getEventViewModel() :AppEventViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppEventViewModel::class.java)
    }
}