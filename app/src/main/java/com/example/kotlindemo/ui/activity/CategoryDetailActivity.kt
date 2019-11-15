package com.example.kotlindemo.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlindemo.Constants
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.entity.CategoryBean
import com.example.kotlindemo.mvp.contract.CategoryDetailContract
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.mvp.presenter.CategoryDetailPresenter
import com.example.kotlindemo.ui.adapter.CategoryDetailAdapter
import kotlinx.android.synthetic.main.activity_category_detail.*

class CategoryDetailActivity : BaseActivity(), CategoryDetailContract.View {

    private val mPresenter by lazy { CategoryDetailPresenter() }

    private val mAdapter by lazy {
        CategoryDetailAdapter(
            this,
            itemList,
            R.layout.item_category_detail
        )
    }

    private var categoryData: CategoryBean? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    init {
        mPresenter.attachView(this)
    }

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    override fun initData() {
        categoryData = intent.getSerializableExtra(Constants.BUNDLE_CATEGORY_DATA) as CategoryBean?
    }

    override fun layoutId(): Int = R.layout.activity_category_detail

    override fun initView() {
        super.initView()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // 加载headerImage
        Glide.with(this)
            .load(categoryData?.headerImage)
            .placeholder(R.color.color_darker_gray)
            .into(imageView)

        tv_category_desc.text ="#${categoryData?.description}#"

        collapsing_toolbar_layout.title = categoryData?.name
        collapsing_toolbar_layout.setExpandedTitleColor(Color.WHITE) //设置还没收缩时状态下字体颜色
        collapsing_toolbar_layout.setCollapsedTitleTextColor(Color.BLACK) //设置收缩后Toolbar上字体的颜色

        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
        //实现自动加载
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView.layoutManager?.itemCount
                val lastVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (itemCount != null) {
                    if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                        loadingMore = true
                        mPresenter.loadMoreData()
                    }
                }
            }
        })
    }

    override fun start() {
        //获取当前分类列表
        categoryData?.id?.let { mPresenter.getCategoryDetailList(it) }
    }

    override fun showLoading() {

    }

    override fun dismissLoading() {

    }
    override fun setCateDetailList(itemList: ArrayList<HomeBean.Issue.Item>) {
        loadingMore = false
        mAdapter.addData(itemList)
    }

    override fun showError(errorMsg: String) {
        multipleStatusView.showError()
    }


    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

}
