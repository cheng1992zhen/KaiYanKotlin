package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment

class MineFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
    }

    override fun lazyLoad() {
    }

    companion object {
        fun getInstance(title: String): MineFragment {
            val mineFragment = MineFragment()
            mineFragment.arguments = Bundle()
            mineFragment.mTitle = title
            return mineFragment
        }
    }
}