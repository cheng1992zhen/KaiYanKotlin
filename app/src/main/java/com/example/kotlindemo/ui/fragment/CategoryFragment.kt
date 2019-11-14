package com.example.kotlindemo.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager

import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.entity.CategoryBean
import com.example.kotlindemo.mvp.contract.CategoryContract
import com.example.kotlindemo.mvp.presenter.CategoryPresenter
import com.example.kotlindemo.ui.adapter.CategoryAdapter
import com.example.kotlindemo.util.showToast
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import com.hazz.kotlinmvp.net.exception.ExceptionHandle
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : BaseFragment(), CategoryContract.View {
    private val mPresenter by lazy { CategoryPresenter() }
    private var mCategoryList = ArrayList<CategoryBean>()
    private val mAdapter by lazy {
        activity?.let {
            CategoryAdapter(it, mCategoryList, R.layout.item_category)
        }
    }

    override fun showCategory(categoryList: ArrayList<CategoryBean>) {
        mCategoryList = categoryList
        mAdapter?.setData(mCategoryList)
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

    var mTitle: String? = null
    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun initView() {
        mPresenter.attachView(this)
        mLayoutStatusView = multipleStatusView
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.adapter = mAdapter
    }

    override fun lazyLoad() {
        mPresenter.getCategoryData()
    }

    companion object {
        fun getInstance(title: String): CategoryFragment {
            val fragment = CategoryFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

}
