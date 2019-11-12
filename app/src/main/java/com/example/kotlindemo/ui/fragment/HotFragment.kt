package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment

class HotFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(title: String): HotFragment {
            val hotFragment = HotFragment()
            hotFragment.arguments = Bundle()
            hotFragment.mTitle = title
            return hotFragment
        }
    }
}