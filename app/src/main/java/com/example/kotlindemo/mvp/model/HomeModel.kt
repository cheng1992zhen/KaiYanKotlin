package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.http.RetrofitManager
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class HomeModel {
    fun requestHomeData(num: Int): Observable<HomeBean> {

        return RetrofitManager.service.getFirstHomeData(num)
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url: String): Observable<HomeBean> {

        return RetrofitManager.service.getMoreHomeData(url)
            .compose(SchedulerUtils.ioToMain())
    }

}