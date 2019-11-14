package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.CategoryContract
import com.example.kotlindemo.mvp.model.CategoryModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class CategoryPresenter : BasePresenter<CategoryContract.View>(), CategoryContract.Presenter {

    private val mModel by lazy { CategoryModel() }

    override fun getCategoryData() {
        checkViewAttached()
        mRootView?.showLoading()
        var disposable = mModel.getCategoryData()
            .subscribe({ categoryList ->
                mRootView?.apply {
                    dismissLoading()
                    showCategory(categoryList)
                }
            }, { throwable ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }

            })
        addSubscription(disposable)
    }
}