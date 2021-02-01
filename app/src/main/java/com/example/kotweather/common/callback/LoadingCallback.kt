package com.example.kotweather.common.callback

import com.example.kotweather.R
import com.kingja.loadsir.callback.Callback

class LoadingCallback: Callback() {
    override fun onCreateView(): Int = R.layout.layout_loading
}