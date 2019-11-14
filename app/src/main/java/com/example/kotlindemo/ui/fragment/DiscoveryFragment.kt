package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.base.BaseFragmentAdapter
import com.example.kotlindemo.util.StatusBarUtil
import com.example.kotlindemo.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_hot.*

class DiscoveryFragment : BaseFragment() {
    private val tabList = ArrayList<String>()

    private val fragments = ArrayList<Fragment>()

    private var mTitle: String? = null

    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
        tv_header_title.text = mTitle
        tabList.add("关注")
        tabList.add("分类")
        fragments.add(FollowFragment.getInstance("关注"))
        fragments.add(CategoryFragment.getInstance("分类"))

        /**
         * getSupportFragmentManager() 替换为getChildFragmentManager()
         */
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragments, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
//        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)
    }

    override fun lazyLoad() {

    }

    companion object {
        fun getInstance(title: String): DiscoveryFragment {
            val discoveryFragment = DiscoveryFragment()
            discoveryFragment.arguments = Bundle()
            discoveryFragment.mTitle = title
            return discoveryFragment
        }
    }
}