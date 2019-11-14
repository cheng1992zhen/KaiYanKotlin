package com.example.kotlindemo.ui.activity

import android.os.Bundle
import android.transition.Transition
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.VideoDetailContract
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.mvp.presenter.VideoDetailPresenter
import com.example.kotlindemo.ui.adapter.VideoDetailAdapter
import com.scwang.smartrefresh.header.MaterialHeader
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import java.text.SimpleDateFormat

class VideoDetailActivity : BaseActivity(), VideoDetailContract.View {

    companion object {
        const val IMG_TRANSITION = "IMG_TRANSITION"
        const val TRANSITION = "TRANSITION"
    }

    /**
     * 第一次调用的时候初始化
     */
    private val mPresenter by lazy { VideoDetailPresenter() }

   // private val mAdapter by lazy { VideoDetailAdapter(this, itemList) }

    private val mFormat by lazy { SimpleDateFormat("yyyyMMddHHmmss"); }


    /**
     * Item 详细数据
     */
    private lateinit var itemData: HomeBean.Issue.Item
    private var orientationUtils: OrientationUtils? = null

    private var itemList = java.util.ArrayList<HomeBean.Issue.Item>()

    private var isPlay: Boolean = false
    private var isPause: Boolean = false


    private var isTransition: Boolean = false

    private var transition: Transition? = null
    private var mMaterialHeader: MaterialHeader? = null



    override fun layoutId(): Int = R.layout.activity_video_detail

    override fun setVideo(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setVideoInfo(itemInfo: HomeBean.Issue.Item) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setBackground(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setRecentRelatedVideo(itemList: ArrayList<HomeBean.Issue.Item>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setErrorMsg(errorMsg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
