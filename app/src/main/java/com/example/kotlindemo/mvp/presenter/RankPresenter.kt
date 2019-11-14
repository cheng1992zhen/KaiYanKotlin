package com.example.kotlindemo.mvp.presenter


import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.RankContract
import com.example.kotlindemo.mvp.model.RankModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle


class RankPresenter : BasePresenter<RankContract.View>(), RankContract.Presenter {

    private val rankModel by lazy { RankModel() }


    /**
     *  请求排行榜数据
     */
    override fun requestRankList(apiUrl: String) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = rankModel.requestRankList(apiUrl)
            .subscribe({ issue ->
                mRootView?.apply {
                    dismissLoading()
                    setRankList(issue.itemList)
                }
            }, { throwable ->
                mRootView?.apply {
                    //处理异常
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }
            })
        addSubscription(disposable)
    }
}