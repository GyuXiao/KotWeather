package com.example.kotweather.module.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R
import com.example.kotweather.model.HourlyWeather

class HourlyAdapter(layout: Int, listData: ArrayList<HourlyWeather>?) :
        BaseQuickAdapter<HourlyWeather, BaseViewHolder>(layout, listData), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: HourlyWeather) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.hourly_time, item.time)
                        .setText(R.id.hourly_weather, item.weather)
                        .setImageResource(R.id.hourly_weather_img, item.weatherImg)
                        .setText(R.id.hourly_wind_ori, item.windOri)
                        .setText(R.id.hourly_wind_level, item.windLevel)
                        .setText(R.id.hourly_air_level, item.airLevel)
                        .setText(R.id.hourly_temp, "${item.temp}℃")
            }
        }
    }
}