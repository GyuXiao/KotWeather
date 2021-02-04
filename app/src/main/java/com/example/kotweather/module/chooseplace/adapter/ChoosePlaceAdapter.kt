package com.example.kotweather.module.chooseplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R
import com.example.kotweather.common.setAdapterAnimation
import com.example.kotweather.model.Place

class ChoosePlaceAdapter(layout: Int, listData: MutableList<Place>?) :
        BaseQuickAdapter<Place, BaseViewHolder>(
                layout, listData), LoadMoreModule
{
    init {
        setAdapterAnimation(2)
    }
    override fun convert(holder: BaseViewHolder, item: Place) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.location_name, item.name)
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        setAdapterAnimation(2)
    }
}