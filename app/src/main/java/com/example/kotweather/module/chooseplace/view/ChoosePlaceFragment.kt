package com.example.kotweather.module.chooseplace.view

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.example.kotweather.R
import com.example.kotweather.base.view.BaseLifeCycleFragment
import com.example.kotweather.common.Constant
import com.example.kotweather.common.getActivityMessageViewModel
import com.example.kotweather.common.util.CommonUtil
import com.example.kotweather.common.util.SPreference
import com.example.kotweather.module.chooseplace.adapter.ChoosePlaceAdapter
import com.example.kotweather.module.chooseplace.viewModel.ChoosePlaceViewModel
import kotlinx.android.synthetic.main.custom_bar.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import com.example.kotweather.databinding.FragmentListBinding
import com.example.kotweather.model.ChoosePlaceData
import com.example.kotweather.model.Place
import com.example.kotweather.model.RealTime


class ChoosePlaceFragment: BaseLifeCycleFragment<ChoosePlaceViewModel, FragmentListBinding>() {

    private lateinit var mAdapter: ChoosePlaceAdapter

    private lateinit var mHeaderView: View

    override fun getLayoutId() = R.layout.fragment_list

    override fun initView() {
        super.initView()
        initRefresh()
        initAdapter()
        initHeaderView()
    }

    override fun initData() {
        if (mSrlRefresh.isRefreshing) {
            mSrlRefresh.isRefreshing = false
        }
        mViewModel.queryAllChoosePlace()
    }

    override fun initDataObserver() {
        mViewModel.mChoosePlaceData.observe(this, Observer { response ->
            response?.let {
                if (response.size == 0) {
                    // 没有添加城市前将选择权交给用户
                    CommonUtil.showToast(requireContext(), "请先添加城市~")
                    mHeaderView.detail_start.setOnClickListener {
                        CommonUtil.showToast(requireContext(), "请先添加城市~")
                    }
                } else {
                    mHeaderView.detail_start.setOnClickListener {
                        Navigation.findNavController(it).navigateUp()
                    }
                }
                setPlaceList(response)
            }
        })
        getActivityMessageViewModel().addChoosePlace.observe(this, Observer {
            it?.let {
                mViewModel.queryAllChoosePlace()
                mAdapter.notifyDataSetChanged()
            }
        })
        // 首页未滑动前显示的城市，是一个被观察对象，因为点击某个城市后需要跳转到首页显示该城市的具体天气信息
        appViewModel.currentPlace.observe(this, Observer {
            it?.let {
                getActivityMessageViewModel().changeCurrentPlace.postValue(true)
            }
        })
        showSuccess()
    }

    private fun initRefresh() {
        // 设置下拉刷新新的loading颜色
        mSrlRefresh.setProgressBackgroundColorSchemeColor(
                ContextCompat.getColor(requireContext(), R.color.blueBackground)
        )
        mSrlRefresh.setColorSchemeColors(Color.WHITE)
        mSrlRefresh.setOnRefreshListener { initData() }
    }

    private fun initHeaderView() {
        mHeaderView = View.inflate(requireActivity(), R.layout.custom_bar, null)
        mHeaderView.apply {
            detail_title.text = "添加的城市"
            detail_start.visibility = View.VISIBLE
            detail_end.visibility = View.VISIBLE
            detail_end.setOnClickListener {
                Navigation.findNavController(it).navigate(
                        R.id.action_choosePlaceFragment_to_searchPlaceFragment)
            }
            detail_start.setOnClickListener {
                Navigation.findNavController(it).navigateUp()
            }
        }
        mAdapter.addHeaderView(mHeaderView)
    }

    private fun initAdapter() {
        mAdapter = ChoosePlaceAdapter(R.layout.place_item, null)
        mRvArticle?.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mRvArticle.adapter = mAdapter
        // 这里长按的话，显示删除提示图样
        mAdapter.setOnItemLongClickListener { adapter, view, position ->
            mAdapter.getViewByPosition(position + 1, R.id.location_delete)?.visibility =
                    View.VISIBLE
            mAdapter.getViewByPosition(position + 1, R.id.location_card)?.setBackgroundColor(
                    ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_80
                    ))
            // “删除”按钮的点击事件
            mAdapter.getViewByPosition(position + 1, R.id.location_delete)?.setOnClickListener {
                val place = mAdapter.getItem(position)
                place?.let {
                    // 二次确认删除弹窗
                    MaterialDialog(requireContext()).show {
                        title(R.string.title)
                        message(R.string.delete_city)
                        cornerRadius(8.0f)
                        negativeButton(R.string.cancel) {
                            mAdapter.getViewByPosition(
                                    position + 1,
                                    R.id.location_delete
                            )?.visibility = View.GONE

                            mAdapter.getViewByPosition(
                                    position + 1,
                                    R.id.location_card
                            )?.setBackgroundColor(
                                    ContextCompat.getColor(
                                            requireContext(),
                                            R.color.blueBackground
                                    )
                            )
                        }
                        positiveButton(R.string.delete) {
                            mViewModel.deletePlace(place.name)
                            mViewModel.deleteChoosePlace(place)
                            getActivityMessageViewModel().addPlace.postValue(true)
                            mAdapter.getViewByPosition(position+1, R.id.location_delete)?.visibility = View.GONE
                            mAdapter.notifyDataSetChanged()
                        }
                    }
                }
                true
            }
            true
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            appViewModel.changeCurrentPlace(position) // 跳转
            Navigation.findNavController(view).navigateUp()
        }
    }


    private fun setPlaceList(addedPlaceList: MutableList<ChoosePlaceData>) {
        mAdapter.setNewInstance(addedPlaceList)
    }
}