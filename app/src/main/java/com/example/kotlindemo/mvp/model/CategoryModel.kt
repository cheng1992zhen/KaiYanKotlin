package com.example.kotlindemo.mvp.model

import com.example.kotlindemo.entity.CategoryBean
import com.example.kotlindemo.http.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CategoryModel {
    /**
     * 获取分类信息
     */
    fun getCategoryData(): Observable<ArrayList<CategoryBean>> {
        return RetrofitManager.service.getCategory()
            .compose(SchedulerUtils.ioToMain())
    }
}