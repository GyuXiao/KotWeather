package com.example.kotweather.common

import com.chad.library.adapter.base.BaseQuickAdapter

/**
 * 设置适配器的列表动画
 */
fun BaseQuickAdapter<*, *>.setAdapterAnimation(mode: Int){
    if(mode == 0){
        this.animationEnable = false
    } else {
        this.animationEnable = true
        this.setAnimationWithDefault(BaseQuickAdapter.AnimationType.values()[2])
    }
}