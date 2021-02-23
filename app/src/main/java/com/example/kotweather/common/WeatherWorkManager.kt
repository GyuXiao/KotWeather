package com.example.kotweather.common

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.module.app.AppViewModel

// WorkManager的使用第一步
// 定义一个后台任务，并实现具体的任务逻辑
class WeatherWorkManager(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val mAppViewModel =
        BaseApplication.instance.getAppViewModelProvider().get(AppViewModel::class.java)

    // doWork()方法不会运行在主线程当中，因此可以放心地
    override fun doWork(): Result {
        mAppViewModel.queryAllChoosePlace()
        return Result.success()
    }
}