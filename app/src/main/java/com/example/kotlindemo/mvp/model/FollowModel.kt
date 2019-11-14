package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.http.RetrofitManager
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class FollowModel {
    /**
     * 获取关注信息
     */
    fun requestFollowList(): Observable<HomeBean.Issue> {

        return RetrofitManager.service.getFollowInfo()
            .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 加载更多
     */
    fun loadMoreData(url:String): Observable<HomeBean.Issue> {
        return RetrofitManager.service.getIssueData(url)
            .compose(SchedulerUtils.ioToMain())
    }
}