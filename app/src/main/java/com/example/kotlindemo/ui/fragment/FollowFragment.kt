package com.example.kotlindemo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.entity.CategoryBean
import com.example.kotlindemo.mvp.contract.FollowContract
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.mvp.presenter.FollowPresenter
import com.example.kotlindemo.ui.adapter.FollowAdapter
import com.example.kotlindemo.util.showToast
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.fragment_follow.*

/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : BaseFragment(), FollowContract.View {
    /**
     * 是否加载更多
     */
    private var loadingMore = false
    var mTitle: String? = null
    private val mPresenter by lazy { FollowPresenter() }
    private var itemList = ArrayList<HomeBean.Issue.Item>()
    private val mAdapter by lazy {
        activity?.let {
            FollowAdapter(it, itemList)
        }
    }

    override fun setFollowInfo(issue: HomeBean.Issue) {
        loadingMore = false
        itemList = issue.itemList
        mAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            mLayoutStatusView?.showNoNetwork()
        } else {
            mLayoutStatusView?.showError()
        }
    }

    override fun showLoading() {
        mLayoutStatusView?.showLoading()
    }

    override fun dismissLoading() {
        mLayoutStatusView?.showContent()
    }

    override fun getLayoutId(): Int = R.layout.fragment_follow


    override fun initView() {
        mPresenter.attachView(this)
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var itemCount = mRecyclerView.layoutManager?.itemCount
                var lastVisibleItem =
                    (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                if (itemCount != null) {
                    if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                        loadingMore = true
                        mPresenter.loadMoreData()
                    }
                }
            }
        })

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        mPresenter.requestFollowList()
    }


    companion object {
        fun getInstance(title: String): FollowFragment {
            val fragment = FollowFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment

        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}
