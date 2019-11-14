package com.example.kotlindemo.mvp.contract

import com.example.kotlindemo.base.IPresenter
import com.example.kotlindemo.base.IView
import com.example.kotlindemo.entity.CategoryBean

interface CategoryContract {
    interface View : IView {
        /**
         * 显示分类的信息
         */
        fun showCategory(categoryList: ArrayList<CategoryBean>)

        /**
         * 显示错误信息
         */
        fun showError(errorMsg: String, errorCode: Int)
    }

    interface Presenter : IPresenter<View> {
        /**
         * 获取分类的信息
         */
        fun getCategoryData()
    }
}