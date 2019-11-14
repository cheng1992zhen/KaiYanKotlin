package com.example.kotlindemo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.mvp.contract.RankContract
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.mvp.presenter.RankPresenter
import com.example.kotlindemo.ui.adapter.CategoryDetailAdapter
import com.example.kotlindemo.util.showToast
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.fragment_rank.*

/**
 * A simple [Fragment] subclass.
 */
class RankFragment : BaseFragment(), RankContract.View {

    private val mPresenter by lazy { RankPresenter() }

    private val mAdapter by lazy {
        activity?.let {
            CategoryDetailAdapter(
                it,
                itemList,
                R.layout.item_category_detail
            )
        }
    }

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var apiUrl: String? = null


    init {
        mPresenter.attachView(this)
    }

    override fun getLayoutId(): Int = R.layout.fragment_rank


    override fun initView() {
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter

        mLayoutStatusView = multipleStatusView
    }

    override fun lazyLoad() {
        if (!apiUrl.isNullOrEmpty()) {
            mPresenter.requestRankList(apiUrl!!)
        }
    }

    override fun setRankList(itemList: ArrayList<HomeBean.Issue.Item>) {
        multipleStatusView.showContent()
        mAdapter?.addData(itemList)
    }

    override fun showError(errorMsg: String, errorCode: Int) {
        showToast(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showError()
        }
    }

    override fun showLoading() {
        multipleStatusView.showLoading()
    }

    override fun dismissLoading() {

    }


    companion object {
        fun getInstance(apiUrl: String): RankFragment {
            val fragment = RankFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            fragment.apiUrl = apiUrl
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}
