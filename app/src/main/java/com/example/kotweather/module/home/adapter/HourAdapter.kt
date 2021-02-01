package com.example.kotweather.module.home.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R

class HourAdapter(layout: Int, listData: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(layout, listData), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.time, item)
                .setText(R.id.temperature, "20")
    }
}