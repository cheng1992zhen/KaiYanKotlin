package com.example.kotlindemo.mvp.model


import com.example.kotlindemo.entity.TabInfoBean
import com.example.kotlindemo.http.RetrofitManager
import com.hazz.kotlinmvp.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

/**
 * Created by xuhao on 2017/11/30.
 * desc: 热门 Model
 */
class HotTabModel {

    /**
     * 获取 TabInfo
     */
    fun getTabInfo(): Observable<TabInfoBean> {

        return RetrofitManager.service.getRankList()
                .compose(SchedulerUtils.ioToMain())
    }

}
