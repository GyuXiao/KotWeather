package com.example.kotweather.common.startup

import android.content.Context
import androidx.startup.Initializer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.kotweather.common.WeatherWorkManager
import java.util.concurrent.TimeUnit

class WeatherStartup : Initializer<WorkManager> {
    override fun create(context: Context): WorkManager {
        val request =
            PeriodicWorkRequest.Builder(WeatherWorkManager::class.java, 15, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(context).enqueue(request)
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}