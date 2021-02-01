package com.example.kotweather.base

import android.app.Application
import com.example.kotweather.common.callback.*
import com.kingja.loadsir.core.LoadSir

/*
 * Description: 获取全局Context的准备
 * @CreateTime：2021/01/03 23:27
 */

open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        initLoadSir()
    }

    // LoadSir是一个加载反馈页管理框架，在加载网络或其他数据时候，
    // 根据需求切换状态页面可添加自定义状态页面，如加载中，加载失败，无数据，网络超时，
    // 如占位图，登录失效等常用页面。可配合网络加载框架，结合返回 状态码，错误码，数据进行状态页自动切换，封装使用效果更佳。
    private fun initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(ErrorCallback())
                .addCallback(LoadingCallback())
                .addCallback(EmptyCallback())
                .commit()
    }
}