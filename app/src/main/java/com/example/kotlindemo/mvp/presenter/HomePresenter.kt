package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.HomeContract

class HomePresenter<T> : BasePresenter<HomeContract.View>(),HomeContract.Presenter {

    override fun requestHomeData(num: Int) {

    }

    override fun loadMoreData() {
    }
}