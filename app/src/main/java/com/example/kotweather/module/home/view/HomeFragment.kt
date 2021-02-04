package com.example.kotweather.module.home.view

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleFragment
import com.example.kotweather.common.Constant
import com.example.kotweather.common.util.CommonUtil
import com.example.kotweather.common.util.SPreference
import com.example.kotweather.databinding.HomeFragmentBinding
import com.example.kotweather.model.Place
import com.example.kotweather.module.home.adapter.HomeDetailAdapter
import com.example.kotweather.module.home.viewmodel.HomeDetailViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.home_detail_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*


class HomeFragment: BaseLifeCycleFragment<HomeDetailViewModel, HomeFragmentBinding>() {

//    private var mPosition: Int by SPreference(Constant.POSITION, 0)

    private val mPlaceNameList = arrayListOf<String>()

    override fun initView() {
        super.initView()
        initToolBar()
        setHasOptionsMenu(true)
    }

    override fun initData() {
        super.initData()
        appViewModel.queryAllPlace()
    }

    override fun getLayoutId() = R.layout.home_fragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_city -> {
                Navigation.findNavController(home_bar)
                    .navigate(R.id.action_homeFragment_to_choosePlaceFragment)
            }
            R.id.action_more -> {
                CommonUtil.showToast(requireContext(), getString(R.string.more))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        appViewModel.mPlaceData.observe(this, Observer { response->
            response?.let {
                if(response.isEmpty()){ // 有点问题
                    Navigation.findNavController(home_normal_view).navigate(R.id.action_homeFragment_to_choosePlaceFragment)
                }
                initHomeDetailFragment(it)
            }
        })
    }

    private fun initToolBar(){
        home_bar.setTitle("")
        (requireActivity() as AppCompatActivity).setSupportActionBar(home_bar)
    }

    private fun initHomeDetailFragment(dataList: MutableList<Place>) {
        val tabs = arrayListOf<String>()
        val fragments = arrayListOf<Fragment>()
        for (data in dataList) {
            tabs.add(data.name)
            fragments.add(
                HomeDetailFragment.newInstance(
                    data.name,
                    data.location.lng,
                    data.location.lat
                )
            )
        }
        mPlaceNameList.clear()
        mPlaceNameList.addAll(tabs)
        if(mPlaceNameList.isNotEmpty()) {// 判空很重要
            home_bar.home_title.text = mPlaceNameList[0]
        }
        home_viewpager.adapter = HomeDetailAdapter(childFragmentManager, tabs, fragments)
        //设置监听
        home_viewpager.addOnPageChangeListener(TitlePageChangeListener())
        indicator_view
            .setSliderColor(
                CommonUtil.getColor(requireContext(), R.color.dark),
                CommonUtil.getColor(requireContext(), R.color.always_white_text)
            )
            .setSliderWidth(resources.getDimension(R.dimen.safe_padding))
            .setSliderHeight(resources.getDimension(R.dimen.safe_padding))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(home_viewpager)
    }

    private inner class TitlePageChangeListener: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            changeTitle(position)
        }
    }

    private fun changeTitle(position: Int) {
        home_bar.home_title.text = mPlaceNameList[position]
    }
}