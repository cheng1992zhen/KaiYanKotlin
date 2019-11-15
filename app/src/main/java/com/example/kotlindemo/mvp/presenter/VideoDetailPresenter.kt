package com.example.kotlindemo.mvp.presenter

import android.app.Activity
import com.example.kotlindemo.base.App
import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.VideoDetailContract
import com.example.kotlindemo.mvp.model.VideoDetailModel
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.util.DisplayManager
import com.example.kotlindemo.util.NetworkUtil
import com.example.kotlindemo.util.dataFormat
import com.example.kotlindemo.util.showToast
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class VideoDetailPresenter : BasePresenter<VideoDetailContract.View>(),
    VideoDetailContract.Presenter {
    private val videoDetailModel: VideoDetailModel by lazy {
        VideoDetailModel()
    }

    /**
     * 加载视频相关的数据
     */
    override fun loadVideoInfo(itemInfo: HomeBean.Issue.Item) {

        val playInfo = itemInfo.data?.playInfo

        val netType = NetworkUtil.isWifi(App.context)
        // 检测是否绑定 View
        checkViewAttached()
        if (playInfo!!.size > 1) {
            // 当前网络是 Wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        break
                    }
                }
            } else {
                //否则就选标清的视频
                for (i in playInfo) {
                    if (i.type == "normal") {
                        val playUrl = i.url
                        mRootView?.setVideo(playUrl)
                        //Todo 待完善
                        (mRootView as Activity).showToast(
                            "本次消耗${(mRootView as Activity)
                                .dataFormat(i.urlList[0].size)}流量"
                        )
                        break
                    }
                }
            }
        } else {
            mRootView?.setVideo(itemInfo.data.playUrl)
        }

        //设置背景
        val backgroundUrl =
            itemInfo.data.cover.blurred + "/thumbnail/${DisplayManager.getScreenHeight()!! - DisplayManager.dip2px(
                250f
            )!!}x${DisplayManager.getScreenWidth()}"
        backgroundUrl.let { mRootView?.setBackground(it) }

        mRootView?.setVideoInfo(itemInfo)

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