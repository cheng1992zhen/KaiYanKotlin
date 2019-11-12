package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment

class DiscoveryFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {

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