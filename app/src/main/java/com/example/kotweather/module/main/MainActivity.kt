package com.example.kotweather.module.main

import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleActivity
import com.example.kotweather.databinding.ActivityMainBinding

class MainActivity : BaseLifeCycleActivity<MainViewModel, ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main
}