package com.example.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kotlindemo.Constants
import com.example.kotlindemo.R
import com.example.kotlindemo.base.App
import com.example.kotlindemo.entity.CategoryBean
import com.example.kotlindemo.ui.activity.CategoryDetailActivity
import com.example.kotlindemo.view.adapter.CommonAdapter
import com.example.kotlindemo.view.recyclerview.ViewHolder

class CategoryAdapter(mContext: Context, categoryList: ArrayList<CategoryBean>, layoutId: Int) :
    CommonAdapter<CategoryBean>(mContext, categoryList, layoutId) {
    private var textTypeface: Typeface? = null

    init {
        textTypeface =
            Typeface.createFromAsset(App.context.assets, "fonts/FZLanTingHeiS-DB1-GB-Regular.TTF")
    }

    override fun bindData(holder: ViewHolder, data: CategoryBean, position: Int) {
        holder.setText(R.id.tv_category_name, "#${data.name}")
        //设置方正兰亭细黑简体
        holder.getView<TextView>(R.id.tv_category_name).typeface = textTypeface

        holder.setImagePath(
            R.id.iv_category,
            object : ViewHolder.HolderImageLoader(data.bgPicture) {
                override fun loadImage(iv: ImageView, path: String) {
                    Glide.with(mContext)
                        .load(path)
                        .placeholder(R.color.color_darker_gray)
                        .transition(DrawableTransitionOptions().crossFade())
                        .thumbnail(0.5f)
                        .into(iv)
                }
            })

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(mContext as Activity, CategoryDetailActivity::class.java)
                intent.putExtra(Constants.BUNDLE_CATEGORY_DATA, data)
                mContext.startActivity(intent)
            }
        })
    }

    /**
     * 设置新数据
     */
    fun setData(categoryList: ArrayList<CategoryBean>) {
        mData.clear()
        mData = categoryList
        notifyDataSetChanged()
    }
}