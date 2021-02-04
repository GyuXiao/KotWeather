package com.example.kotweather.common.util

import android.content.Context
import android.util.DisplayMetrics

object DisplayUtil {
    fun px2dip(context: Context, pxValue: Float): Float {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (pxValue / scale + 0.5f).toFloat()
    }

    fun dip2px(context: Context, dipValue: Int): Float {
        val scale: Float = context.getResources().getDisplayMetrics().density
        return (dipValue * scale + 0.5f).toFloat()
    }

    fun px2sp(context: Context, pxValue: Float): Float {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (pxValue / fontScale + 0.5f).toFloat()
    }

    fun sp2px(context: Context, spValue: Int): Float {
        val fontScale: Float = context.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toFloat()
    }

    fun getScreenWidth(context: Context): Float {
        val dm: DisplayMetrics = context.getResources().getDisplayMetrics()
        return dm.widthPixels.toFloat()
    }

    fun getScreenHeight(context: Context): Float {
        val dm: DisplayMetrics = context.getResources().getDisplayMetrics()
        return dm.heightPixels.toFloat()
    }
}