package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.HomeContract
import com.example.kotlindemo.mvp.model.bean.HomeBean

class HomeFragment : BaseFragment(), HomeContract.View {
    override fun setHomeData(homeBean: HomeBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setMoreData(itemList: ArrayList<HomeBean.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError(msg: String, errorCode: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var mTitle: String? = null
    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun lazyLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun getInstance(title: String): HomeFragment {
            val homeFragment = HomeFragment()
            homeFragment.arguments = Bundle()
            homeFragment.mTitle = title
            return homeFragment
        }
    }


}