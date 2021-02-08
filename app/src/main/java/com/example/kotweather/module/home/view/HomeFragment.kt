package com.example.kotweather.module.home.view

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleFragment
import com.example.kotweather.common.getEventViewModel
import com.example.kotweather.common.init
import com.example.kotweather.common.util.CommonUtil
import com.example.kotweather.databinding.HomeFragmentBinding
import com.example.kotweather.model.Place
import com.example.kotweather.module.home.viewmodel.HomeViewModel
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*


class HomeFragment: BaseLifeCycleFragment<HomeViewModel, HomeFragmentBinding>() {

//    private var mPosition: Int by SPreference(Constant.POSITION, 0)

    private val mPlaceNameList = arrayListOf<String>()

    override fun initView() {
        super.initView()
        initToolBar()
        setHasOptionsMenu(true)
    }

    override fun initData() {
        mViewModel.queryAllPlace()
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
                Navigation.findNavController(home_bar)
                        .navigate(R.id.action_homeFragment_to_aboutFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mPlaceData.observe(this, Observer { response->
            response?.let {
                if(response.size == 0){
                    Navigation.findNavController(home_normal_view).navigate(R.id.action_homeFragment_to_choosePlaceFragment)
                }
                initHomeDetailFragment(it)
                showSuccess()
            }
        })
        getEventViewModel().addPlace.observe(this, Observer {
            it?.let {
                mViewModel.queryAllPlace()
            }
        })

        // 从choosePlaceFragment点击某城市跳转过来，viewPager需要观察是否改变
        getEventViewModel().changeCurrentPlace.observe(this, Observer {
            it?.let {
                home_viewpager.setCurrentItem(appViewModel.currentPlace.value!!, true)
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
                        data.location.lat,
                        data.primaryKey
                )
            )
        }
        mPlaceNameList.clear()
        mPlaceNameList.addAll(tabs)
        if(mPlaceNameList.isNotEmpty()) {// 判空很重要
            home_bar.home_title.text = mPlaceNameList[0]
        }
        home_viewpager.offscreenPageLimit = mPlaceNameList.size
        // viewPager在这里上场
        home_viewpager.init(childFragmentManager, fragments)
        //设置监听
        home_viewpager.addOnPageChangeListener(TitlePageChangeListener())

        indicator_view.setSliderColor(
                CommonUtil.getColor(requireContext(), R.color.grey_10),
                CommonUtil.getColor(requireContext(), R.color.material_blue)
            )
            .setSliderWidth(resources.getDimension(R.dimen.safe_padding))
            .setSliderHeight(resources.getDimension(R.dimen.safe_padding))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(home_viewpager)
        indicator_view.visibility = View.VISIBLE
    }

    private inner class TitlePageChangeListener: ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            indicator_view.visibility = View.VISIBLE
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            changeTitle(position)
            indicator_view.visibility =View.VISIBLE
        }
    }

    private fun changeTitle(position: Int) {
        home_bar.home_title.text = mPlaceNameList[position]
    }
}