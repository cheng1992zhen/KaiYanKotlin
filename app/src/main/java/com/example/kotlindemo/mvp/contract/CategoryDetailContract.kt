package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.mvp.model.bean.HomeBean


/**
 *
 * desc: 分类详情契约类
 */
interface CategoryDetailContract {

    interface View: IView {
        /**
         *  设置列表数据
         */
        fun setCateDetailList(itemList:ArrayList<HomeBean.Issue.Item>)

        fun showError(errorMsg:String)




    }

    interface Presenter: IPresenter<View> {

        fun getCategoryDetailList(id:Long)

        fun loadMoreData()
    }
}