package com.example.kotweather.module.main

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.example.kotweather.R
import com.example.kotweather.base.BaseApplication
import com.example.kotweather.base.view.BaseLifeCycleActivity
import com.example.kotweather.common.getEventViewModel
import com.example.kotweather.common.permission.PermissionResult
import com.example.kotweather.common.permission.Permissions
import com.example.kotweather.common.util.CommonUtil
import com.example.kotweather.databinding.ActivityMainBinding
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import pub.devrel.easypermissions.AppSettingsDialog


class MainActivity : BaseLifeCycleActivity<MainViewModel, ActivityMainBinding>(), OnGeocodeSearchListener {

    private var locationClient: AMapLocationClient? = null
    private var locationOption: AMapLocationClientOption? = null
    private var geocoderSearch: GeocodeSearch? = null
    private var mPlace: Place? = null

    private val mPermissions = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.CHANGE_WIFI_STATE
    )
    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermission()
        geocoderSearch = GeocodeSearch(this)
        geocoderSearch!!.setOnGeocodeSearchListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.nav_main).navigateUp()
    }

    private fun initPermission() {
        Permissions(this).request(*mPermissions).observe(
                this, Observer {
            when (it) {
                is PermissionResult.Grant -> {
                    initLocation()
                }
                // 进入设置界面申请权限
                is PermissionResult.Rationale -> {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    )
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
                // 进入设置界面申请权限
                is PermissionResult.Deny -> {
                    val intent = Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    )
                    intent.data = Uri.parse("package:$packageName")
                    startActivity(intent)
                }
            }
        })
    }

    private fun initLocation() {
        // 初始化client
        locationClient = AMapLocationClient(BaseApplication.instance)
        locationOption = getDefaultOption()

        // 设置定位参数
        locationClient!!.setLocationOption(locationOption)

        // 设置定位监听
        locationClient!!.setLocationListener(locationListener)

        startLocation()
    }

    private fun getDefaultOption(): AMapLocationClientOption? {
        val mOption = AMapLocationClientOption()
        mOption.locationMode = AMapLocationMode.Hight_Accuracy
        mOption.isGpsFirst = true
        mOption.httpTimeOut = 20000
        mOption.isNeedAddress = true
        mOption.isOnceLocation = true
        if(mOption.isOnceLocation){
            mOption.isOnceLocationLatest = true
        }
        mOption.isSensorEnable = false
        mOption.isWifiScan = true
        mOption.isLocationCacheEnable = false
        mOption.geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT
        return mOption
    }

    private var locationListener = AMapLocationListener {location ->
        if(location != null) {
            if(location.errorCode == 0) {
                regeocoder(location.latitude, location.longitude)
            } else {
                CommonUtil.showToast(this, "如果不想打开GPS, 也可以手动搜索哦")
            }
        }
    }

    private fun regeocoder(lat: Double, lng: Double) {
        val query = RegeocodeQuery(
            LatLonPoint(lat, lng), 200F,
            GeocodeSearch.AMAP
        )
        geocoderSearch!!.getFromLocationAsyn(query)
    }

    private fun startLocation() {
        locationClient!!.setLocationOption(locationOption)
        locationClient!!.startLocation()
        CommonUtil.showToast(this, "请打开GPS，开始定位了-（ ゜- ゜）つ口~")
    }

    private fun stopLocation() {
        locationClient!!.stopLocation()
    }

    private fun destroyLocation() {
        if(locationClient != null) {
            locationClient!!.onDestroy()
            locationClient = null
            locationOption = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyLocation()
    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {}

    override fun onRegeocodeSearched(result: RegeocodeResult, rCode: Int) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if(result != null) {
                if (result.getRegeocodeAddress() != null && result.getRegeocodeAddress().getFormatAddress() != null) {
                    mViewModel.searchPlaces(result.regeocodeAddress.formatAddress.substring(3, 6))
                }
            }
        }
    }

    override fun initDataObserver() {
        super.initDataObserver()
        mViewModel.mSearchPlacesData.observe(this, Observer {
            it?.let {
                mPlace = it.places[0]
                mViewModel.insertPlace(mPlace!!)
                mViewModel.loadRealtimeWeather(mPlace!!.location.lng, mPlace!!.location.lat)
            }
        })

        mViewModel.mRealTimeData.observe(this, Observer {
            it?.let {
                // 在这里将当前定位到的地点消息插入数据库，同时insertChoosePlace的操作里也包括将之前定位过的地点重置处理
                mViewModel.insertChoosePlace(
                    ChoosePlaceData(
                        0,
                        true,
                        mPlace!!.name,
                        it.result.realtime.temperature.toInt(),
                        it.result.realtime.skycon
                    )
                )
            }
        })

        mViewModel.mPlaceInsertResult.observe(this, Observer {
            it?.let {
                getEventViewModel().addPlace.postValue(true)
            }
        })

        mViewModel.mChoosePlaceInsertResult.observe(this, Observer {
            it?.let {
                getEventViewModel().addChoosePlace.postValue(true)
            }
        })
    }
}