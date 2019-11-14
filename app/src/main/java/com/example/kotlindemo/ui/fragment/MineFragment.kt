package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.util.StatusBarUtil
import kotlinx.android.synthetic.main.fragment_mine.*

class MineFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_mine

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
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