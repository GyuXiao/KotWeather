package com.example.kotweather.common

import android.content.Context
import android.widget.Toast
import java.lang.reflect.ParameterizedType

object Utils {
    fun showToast(context: Context, string: String) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    fun <T> getClass(t: Any): Class<T> = (t.javaClass.genericSuperclass as ParameterizedType)
        .actualTypeArguments[0]
            as Class<T>

}