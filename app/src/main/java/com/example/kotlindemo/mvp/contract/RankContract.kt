package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.mvp.model.bean.HomeBean


/**
 * Created by xuhao on 2017/11/30.
 * desc: 契约类
 */
interface RankContract {

    interface View : IView {
        /**
         * 设置排行榜的数据
         */
        fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun requestRankList(apiUrl: String)
    }
}