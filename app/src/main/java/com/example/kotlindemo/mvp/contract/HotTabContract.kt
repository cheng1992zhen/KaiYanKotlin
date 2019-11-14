package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.entity.TabInfoBean

interface HotTabContract {
    interface View : IView {
        /**
         * 设置 TabInfo
         */
        fun setTabInfo(tabInfoBean: TabInfoBean)

        fun showError(errorMsg: String, errorCode: Int)
    }


    interface Presenter : IPresenter<View> {
        /**
         * 获取 TabInfo
         */
        fun getTabInfo()
    }

}