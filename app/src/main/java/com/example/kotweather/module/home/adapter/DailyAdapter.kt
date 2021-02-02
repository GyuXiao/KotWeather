package com.example.kotweather.module.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R
import com.example.kotweather.common.DateUtil
import com.example.kotweather.common.getSky
import com.example.kotweather.model.DailyResponse

class DailyAdapter(layout: Int, listData: MutableList<DailyResponse.DailyData>?) :
BaseQuickAdapter<DailyResponse.DailyData, BaseViewHolder>(
        layout, listData
){
    override fun convert(holder: BaseViewHolder, item: DailyResponse.DailyData) {
        holder?.let { holder->
            item?.let {
                holder.setText(R.id.date, DateUtil.getTodayInWeek(item.date))
                        .setImageResource(R.id.weather, getSky(item.value).icon)
                        .setText(R.id.temperature, "${item.min.toInt()}℃~${item.max.toInt()}℃")
            }
        }
    }
}