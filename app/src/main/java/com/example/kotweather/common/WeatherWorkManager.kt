package com.example.kotweather.common

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.module.app.AppViewModel

class WeatherWorkManager(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    private val mAppViewModel =
        BaseApplication.instance.getAppViewModelProvider().get(AppViewModel::class.java)

    override fun doWork(): Result {
        mAppViewModel.queryAllChoosePlace()
        return Result.success()
    }
}