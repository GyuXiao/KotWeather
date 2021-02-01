package com.example.kotweather.module.searchplace.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.example.kotweather.R
import com.example.kotweather.model.Place


class SearchPlaceAdapter(layout: Int, listData: MutableList<Place>?) :
        BaseQuickAdapter<Place, BaseViewHolder>(layout, listData), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: Place) {
        holder?.let { holder ->
            item?.let {
                holder.setText(R.id.placeName, item.name)
                holder.setText(R.id.placeAddress, item.address)
            }
        }
    }
}