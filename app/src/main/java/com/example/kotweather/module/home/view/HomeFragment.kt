package com.example.kotweather.module.home.view

import android.annotation.SuppressLint
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleFragment
import com.example.kotweather.common.Utils
import com.example.kotweather.common.getSky
import com.example.kotweather.databinding.HomeFragmentBinding
import com.example.kotweather.model.DailyResponse
import com.example.kotweather.model.RealtimeResponse
import com.example.kotweather.module.home.adapter.DailyAdapter
import com.example.kotweather.module.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.layout_container.*
import kotlinx.android.synthetic.main.layout_current_place_detail.*
import kotlinx.android.synthetic.main.layout_flipper_detail.*
import kotlinx.android.synthetic.main.life_index.*

class HomeFragment: BaseLifeCycleFragment<HomeViewModel, HomeFragmentBinding>() {
    private lateinit var mAdapter: DailyAdapter

    override fun initView() {
        super.initView()
        setHasOptionsMenu(true)
        initToolBar("")
        initAdapter()
    }

    override fun initData() {
        super.initData()
        mViewModel.queryFirstPlace()
//        handleBundle()
    }

//    private fun handleBundle() {
//        val lng = arguments?.getString("lng")
//        val lat = arguments?.getString("lat")
//        val name = arguments?.getString("placeName")
//        mViewModel.loadRealtimeWeather(lng, lat)
//        mViewModel.loadDailyWeather(lng, lat)
//        initToolBar(name)
//    }

    private fun initToolBar(title : String?){
        home_bar.home_title.text = title
        home_bar.setTitle("")
        (requireActivity() as AppCompatActivity).setSupportActionBar(home_bar)
    }

    override fun initDataObserver() {
        super.initDataObserver()
        appViewModel.currectPlace.observe(this, Observer { it->
            it?.let {
                initToolBar(it.name)
                mViewModel.loadRealtimeWeather(it.location.lng, it.location.lat)
                mViewModel.loadDailyWeather(it.location.lng, it.location.lat)
            }
        })

        mViewModel.mRealtimeData.observe(this, Observer { response->
            response?.let {
                initCurrentData(it.result.realtime)
            }
        })

        mViewModel.mDailyData.observe(this, Observer { response->
            response?.let {
                val dailyDataList = ArrayList<DailyResponse.DailyData>()
                for(i in 0 until it.result.daily.skycon.size){
                    dailyDataList.add(
                            DailyResponse.DailyData(
                                    it.result.daily.skycon[i].date,
                                    it.result.daily.skycon[i].value,
                                    it.result.daily.temperature[i].max,
                                    it.result.daily.temperature[i].min
                            )
                    )
                }
                initDailyData(dailyDataList)
                initDailyIndex(it.result.daily.life_index)
            }
        })
    }

    override fun getLayoutId() = R.layout.home_fragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_city -> {
                Navigation.findNavController(home_container).navigate(R.id.action_homeFragment_to_choosePlaceFragment)
            }
            R.id.action_more -> {
                Utils.showToast(requireContext(), getString(R.string.more))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        mAdapter = DailyAdapter(R.layout.daily_item, null)
        home_recycler.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        home_recycler.adapter = mAdapter
    }

    private fun initDailyData(dailyData: MutableList<DailyResponse.DailyData>){
        mAdapter.setNewInstance(dailyData)
    }

    private fun initDailyIndex(lifeIndex: DailyResponse.LifeIndex){
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
    }

    @SuppressLint("ResourceType")
    private fun initCurrentData(realtime: RealtimeResponse.Realtime) {
        temp_text_view.text = "${realtime.temperature.toInt()} ℃"
        description_text_view.text = getSky(realtime.skycon).info
        animation_view.setImageResource(getSky(realtime.skycon).icon)
        humidity_text_view.text = "湿度: ${realtime.humidity.toString()}"
        wind_text_view.text = "风力: ${realtime.wind.speed.toString()}"
        visible_text_view.text = "能见度: ${realtime.visibility.toString()}"
        index_text_view.text = "空气指数: ${realtime.air_quality.aqi.chn.toInt()}"
    }
}