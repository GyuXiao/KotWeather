package com.example.kotweather.common.util

import java.util.*

object DateUtil {
    fun getTodayInWeek(date: Date): String {
        val list = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        var calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        var index: Int = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if(index < 0){
            index = 0
        }
        return list[index]
    }
}