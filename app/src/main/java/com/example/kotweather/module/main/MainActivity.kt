package com.example.kotweather.module.main

import androidx.navigation.Navigation
import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleActivity
import com.example.kotweather.databinding.ActivityMainBinding

class MainActivity : BaseLifeCycleActivity<AppViewModel, ActivityMainBinding>() {
    override fun getLayoutId() = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_main).navigateUp()
    }
}