package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.VideoDetailContract
import com.example.kotlindemo.mvp.model.VideoDetailModel
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class VideoDetailPresenter :BasePresenter<VideoDetailContract.View>(),VideoDetailContract.Presenter {
    private val videoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }

    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {

    }

    override fun requestRelatedVideo(id: Long) {
        mRootView?.showLoading()
        val disposable = videoDetailModel.requestRelatedData(id)
            .subscribe({ issue ->
                mRootView?.apply {
                    dismissLoading()
                    setRecentRelatedVideo(issue.itemList)
                }
            }, { t ->
                mRootView?.apply {
                    dismissLoading()
                    setErrorMsg(ExceptionHandle.handleException(t))
                }
            })

        addSubscription(disposable)
    }
}