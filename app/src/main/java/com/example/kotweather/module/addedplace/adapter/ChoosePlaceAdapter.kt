package com.example.kotweather.module.addedplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R
import com.example.kotweather.model.Place

class ChoosePlaceAdapter(layout: Int, listData: MutableList<Place>?) :
        BaseQuickAdapter<Place, BaseViewHolder>(
                layout, listData), LoadMoreModule
{
    override fun convert(holder: BaseViewHolder, item: Place) {
        holder?.let { holder->
            item?.let {
                holder.setText(R.id.location_name, item.name)
            }
        }
    }
}