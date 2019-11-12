package com.example.kotlindemo.ui.adapter

import android.content.Context
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.view.adapter.CommonAdapter
import com.example.kotlindemo.view.recyclerview.ViewHolder

class HomeAdapter(context: Context, data: ArrayList<HomeBean.Issue.Item>) :
    CommonAdapter<HomeBean.Issue.Item>(context, data, -1) {
    // banner 作为 RecyclerView 的第一项
    var bannerItemSize = 0
    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {

    }

    /**
     * 设置 Banner 大小
     */
    fun setBannerSize(count: Int) {
        bannerItemSize = count
    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.mData.addAll(itemList)
        notifyDataSetChanged()
    }
}