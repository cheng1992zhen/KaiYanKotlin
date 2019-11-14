package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.http.RetrofitManager
import com.example.kotlindemo.mvp.model.bean.HomeBean

import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable


class VideoDetailModel {

    fun requestRelatedData(id:Long):Observable<HomeBean.Issue>{
        return RetrofitManager.service.getRelatedData(id)
                .compose(SchedulerUtils.ioToMain())
    }

}