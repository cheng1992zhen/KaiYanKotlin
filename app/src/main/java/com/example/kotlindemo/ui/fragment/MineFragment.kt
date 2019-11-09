package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.base.BaseFragment

class MineFragment : BaseFragment() {
    private var mTitle: String? = null
    override fun getLayoutId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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