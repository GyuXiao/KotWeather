package com.example.kotweather.module.about

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.kotweather.R
import com.example.kotweather.module.main.MainActivity
import kotlinx.android.synthetic.main.custom_bar.view.*

class AboutFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var parentActivity: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //这里重写根据PreferenceFragmentCompat 的布局 ，往他的根布局插入了一个toolbar
        val containerView = view.findViewById<FrameLayout>(android.R.id.list_container)
        containerView.let {
            val linearLayout = it.parent as? LinearLayout
            linearLayout?.run {
                val toolbarView = LayoutInflater.from(activity).inflate(R.layout.custom_bar, null)
                toolbarView.detail_start.setOnClickListener {
                    Navigation.findNavController(it).navigateUp()
                }
                toolbarView.detail_end.visibility = View.INVISIBLE
                toolbarView.detail_title.text = "设置"
                addView(toolbarView, 0)
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_fragment)
        parentActivity = activity as MainActivity
        init()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    private fun init() {

        findPreference<Preference>("简书")?.setOnPreferenceClickListener {
            view?.let {
                Navigation.findNavController(it)
                        .navigate(R.id.action_aboutFragment_to_webFragment, Bundle().apply {
                            putString("title", "mifankai")
                            putString("url", "https://www.jianshu.com/u/b03dcb8185b5")
                        })
            }
            false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
    }
}