package com.example.kotweather.common

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

object KeyboardUtils {
    // 关闭软键盘
    fun Fragment.hideKeyboard() {
        // 当前焦点的 View
        val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
    }
}