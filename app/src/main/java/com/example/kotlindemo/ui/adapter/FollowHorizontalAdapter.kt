package com.example.kotlindemo.ui.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.kotlindemo.Constants
import com.example.kotlindemo.R
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.ui.activity.VideoDetailActivity
import com.example.kotlindemo.util.durationFormat
import com.example.kotlindemo.view.adapter.CommonAdapter
import com.example.kotlindemo.view.recyclerview.ViewHolder

class FollowHorizontalAdapter(
    mContext: Context,
    categoryList: ArrayList<HomeBean.Issue.Item>,
    layoutId: Int
) : CommonAdapter<HomeBean.Issue.Item>(mContext, categoryList, layoutId) {

    override fun bindData(holder: ViewHolder, data: HomeBean.Issue.Item, position: Int) {
        val horizontalItemData = data.data
        holder.setImagePath(
            R.id.iv_cover_feed,
            object : ViewHolder.HolderImageLoader(data.data?.cover?.feed!!) {
                override fun loadImage(iv: ImageView, path: String) {
                    // 加载封页图
                    Glide.with(mContext)
                        .load(path)
                        .placeholder(R.drawable.placeholder_banner)
                        .transition(DrawableTransitionOptions().crossFade())
                        .into(holder.getView(R.id.iv_cover_feed))
                }

            })

        //横向 RecyclerView 封页图下面标题
        holder.setText(R.id.tv_title, horizontalItemData?.title ?: "")
        // 格式化时间
        val timeFormat = durationFormat(horizontalItemData?.duration)
        //标签
        with(holder) {
            if (horizontalItemData?.tags != null && horizontalItemData.tags.size > 0) {
                setText(R.id.tv_tag, "#${horizontalItemData.tags[0].name} / $timeFormat")
            } else {
                setText(R.id.tv_tag, "#$timeFormat")
            }

            setOnItemClickListener(listener = View.OnClickListener {
                goToVideoPlayer(mContext as Activity, holder.getView(R.id.iv_cover_feed), data)
            })
        }
    }

    /**
     * 跳转到视频详情页面播放
     *
     * @param activity
     * @param view
     */
    private fun goToVideoPlayer(activity: Activity, view: View, itemData: HomeBean.Issue.Item) {
        val intent = Intent(activity, VideoDetailActivity::class.java)
        intent.putExtra(Constants.BUNDLE_VIDEO_DATA, itemData)
        intent.putExtra(VideoDetailActivity.TRANSITION, true)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val pair = Pair(view, VideoDetailActivity.IMG_TRANSITION)
            val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, pair
            )
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
        } else {
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }
}