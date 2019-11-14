package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.FollowContract
import com.example.kotlindemo.mvp.model.FollowModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class FollowPresenter : BasePresenter<FollowContract.View>(), FollowContract.Presenter {
    private val mModel by lazy { FollowModel() }
    private var nextPageUrl: String? = null
    override fun requestFollowList() {
        checkViewAttached()
        mRootView?.showLoading()
        var disposable = mModel.requestFollowList()
            .subscribe({ issue ->
                mRootView?.run {
                    dismissLoading()
                    nextPageUrl = issue.nextPageUrl
                    setFollowInfo(issue)
                }

            }, { throwable ->
                mRootView?.apply {
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }

            })
        addSubscription(disposable)
    }


    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            mModel.loadMoreData(it)
                .subscribe({ issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setFollowInfo(issue)
                    }

                }, { throwable ->
                    mRootView?.apply {
                        showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }

                })
        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }

}