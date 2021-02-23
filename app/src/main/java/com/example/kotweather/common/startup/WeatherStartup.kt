package com.example.kotweather.common.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.kotweather.common.WeatherWorkManager
import java.util.concurrent.TimeUnit

class WeatherStartup : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        // WorkManager的使用第二步，配置该任务的运行条件和约束信息
        // 这里使用的是周期性运行的后台任务请求
        val request =
            PeriodicWorkRequest.Builder(WeatherWorkManager::class.java, 15, TimeUnit.MINUTES)
                .build()
        // WorkManager的使用第三步，将构建的后台任务请求传入WorkManager的enqueue()方法
        WorkManager.getInstance(context).enqueue(request)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}