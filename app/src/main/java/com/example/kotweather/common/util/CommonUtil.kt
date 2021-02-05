package com.example.kotweather.common.util

import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.lang.reflect.ParameterizedType

object CommonUtil {
    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    // 通过反射 获取父亲泛型T 对应的 Class类
    fun <T> getClass(t: Any): Class<T> = (t.javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[0]
            as Class<T>

    fun getColor(context: Context, color: Int): Int{
        return ContextCompat.getColor(context, color)
    }

    fun getNightString(skycon : String) : Boolean {
        return skycon.contains("night", ignoreCase = true)
    }
}