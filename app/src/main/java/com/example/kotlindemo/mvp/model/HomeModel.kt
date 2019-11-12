package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.http.RetrofitManager
import com.example.kotlindemo.mvp.model.bean.HomeBean
import io.reactivex.Observable

class HomeModel {
    fun requestHomeData(num: Int): Observable<HomeBean> {

        return RetrofitManager.service.getFirstHomeData(num)

    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean> {

        return RetrofitManager.service.getMoreHomeData(url)
    }

}