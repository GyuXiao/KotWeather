package com.example.kotweather.network

import com.example.kotweather.common.Constant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory private constructor() {
    private val retrofit: Retrofit

    // (2)
    // 有了Retrofit对象后，可以调用它的create()方法，并传入具体Service接口所对应的Class类型，
    // 并创建一个该接口的动态代理对象。有了这个动态代理对象，我们就可以随意调用接口中定义的方法
    fun <T> createRetrofit(serviceClass: Class<T>): T = retrofit.create(serviceClass)


    // (1)
    // Retrofit.Builder()构建一个Retrofit对象
    // baseUrl()方法用于指定所有Retrofit请求的根路径
    // addConverterFactory()方法用于指定Retrofit在解析数据时所用的转换库，这里指定为GsonConverterFactory
    init {
        retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }
}