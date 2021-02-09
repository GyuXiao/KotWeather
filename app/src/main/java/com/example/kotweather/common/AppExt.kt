package com.example.kotweather.common

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.module.app.AppViewModel
import com.example.kotweather.module.app.AppEventViewModel

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

fun Activity.getEventViewModel() : AppEventViewModel {
    BaseApplication.instance.let {
        return it.getAppViewModelProvider().get(AppEventViewModel::class.java)
    }
}