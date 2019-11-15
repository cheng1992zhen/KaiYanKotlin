package com.example.kotlindemo.ui.activity

import android.annotation.TargetApi
import android.graphics.Typeface
import android.os.Build
import android.transition.Fade
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.R
import com.example.kotlindemo.base.App
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.example.kotlindemo.mvp.presenter.SearchPresenter
import com.example.kotlindemo.ui.adapter.CategoryDetailAdapter
import com.example.kotlindemo.ui.adapter.HotKeywordsAdapter
import com.example.kotlindemo.util.CleanLeakUtils
import com.example.kotlindemo.util.StatusBarUtil
import com.example.kotlindemo.util.showToast
import com.example.kotlindemo.view.ViewAnimUtils
import com.google.android.flexbox.*
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchContract.View {
    private val mPresenter by lazy { SearchPresenter() }

    private val mResultAdapter by lazy {
        CategoryDetailAdapter(
            this,
            itemList,
            R.layout.item_category_detail
        )
    }

    private var mHotKeywordsAdapter: HotKeywordsAdapter? = null

    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private var mTextTypeface: Typeface? = null

    private var keyWords: String? = null

    /**
     * 是否加载更多
     */
    private var loadingMore = false

    init {
        mPresenter.attachView(this)
        //细黑简体字体
        mTextTypeface =
            Typeface.createFromAsset(App.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }


    /**
     * 进入页面
     */
    override fun initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setUpEnterAnimation() // 入场动画
            setUpExitAnimation() // 退场动画
        } else {
            setUpView()
        }
    }

    override fun initView() {
        tv_title_tip.typeface = mTextTypeface
        tv_hot_search_words.typeface = mTextTypeface
        //初始化查询结果的 RecyclerView
        mRecyclerView_result.layoutManager = LinearLayoutManager(this)
        mRecyclerView_result.adapter = mResultAdapter

        //实现自动加载
        mRecyclerView_result.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val itemCount = mRecyclerView_result.layoutManager?.itemCount
                val lastVisibleItem =
                    (mRecyclerView_result.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (itemCount != null) {
                    if (!loadingMore && lastVisibleItem == (itemCount - 1)) {
                        loadingMore = true
                        mPresenter.loadMoreData()
                    }
                }
            }
        })

        //取消
        tv_cancel.setOnClickListener { onBackPressed() }
        //键盘的搜索按钮
        et_search_view.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                closeSoftKeyboard()
                keyWords = et_search_view.text.toString().trim()
                if (keyWords.isNullOrEmpty()) {
                    showToast("请输入你感兴趣的关键词")
                } else {
                    mPresenter.querySearchData(keyWords!!)
                }
            }
            false
        }
        mLayoutStatusView = multipleStatusView

        //状态栏透明和间距处理
        StatusBarUtil.darkMode(this)
        StatusBarUtil.setPaddingSmart(this, toolbar)
    }

    private fun setUpView() {
        val animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        animation.duration = 300
        rel_container.startAnimation(animation)
        rel_container.visibility = View.VISIBLE
        //打开软键盘
        openKeyBord(et_search_view, applicationContext)
    }

    override fun layoutId(): Int = R.layout.activity_search
    override fun onStart() {
        super.onStart()
        //请求热门关键词
        mPresenter.requestHotWordData()
    }
    /**
     * 设置热门关键词
     */
    override fun setHotWordData(string: ArrayList<String>) {
        showHotWordView()
        mHotKeywordsAdapter = HotKeywordsAdapter(this, string, R.layout.item_flow_text)

        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式

        mRecyclerView_hot.layoutManager = flexBoxLayoutManager
        mRecyclerView_hot.adapter = mHotKeywordsAdapter

        mHotKeywordsAdapter?.setOnTagItemClickListener {
            closeSoftKeyboard()
            keyWords = it
            mPresenter.querySearchData(it)
        }
    }
    /**
     * 设置搜索结果
     */
    override fun setSearchResult(issue: HomeBean.Issue) {
        loadingMore = false
        hideHotWordView()

        tv_search_count.visibility = View.VISIBLE
        tv_search_count.text = String.format(resources.getString(R.string.search_result_count), keyWords, issue.total)

        itemList = issue.itemList
        mResultAdapter.addData(issue.itemList)
    }
    /**
     * 隐藏热门关键字的 View
     */
    private fun hideHotWordView(){
        layout_hot_words.visibility = View.GONE
        layout_content_result.visibility = View.VISIBLE
    }

    override fun closeSoftKeyboard() {
        closeKeyBord(et_search_view, applicationContext)
    }

    override fun setEmptyView() {
        showToast("抱歉，没有找到相匹配的内容")
        hideHotWordView()
        tv_search_count.visibility = View.GONE
        mLayoutStatusView?.showEmpty()
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

    /**
     * 显示热门关键字的 流式布局
     */
    private fun showHotWordView(){
        layout_hot_words.visibility = View.VISIBLE
        layout_content_result.visibility = View.GONE
    }

    /**
     * 退场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpExitAnimation() {
        val fade = Fade()
        window.returnTransition = fade
        fade.duration = 300
    }

    /**
     * 进场动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setUpEnterAnimation() {
        val transition = TransitionInflater.from(this)
            .inflateTransition(R.transition.arc_motion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {

            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                animateRevealShow()
            }

            override fun onTransitionCancel(transition: Transition) {

            }

            override fun onTransitionPause(transition: Transition) {

            }

            override fun onTransitionResume(transition: Transition) {

            }
        })
    }

    /**
     * 展示动画
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateRevealShow() {
        ViewAnimUtils.animateRevealShow(
            this, rel_frame,
            fab_circle.width / 2, R.color.backgroundColor,
            object : ViewAnimUtils.OnRevealAnimationListener {
                override fun onRevealHide() {

                }

                override fun onRevealShow() {
                    setUpView()
                }
            })
    }

    // 默认回退
    private fun defaultBackPressed() {
        closeSoftKeyboard()
        super.onBackPressed()
    }

    override fun onDestroy() {
        CleanLeakUtils.fixInputMethodManagerLeak(this)
        super.onDestroy()
        mPresenter.detachView()
        mTextTypeface = null
    }


    // 返回事件
    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewAnimUtils.animateRevealHide(
                this, rel_frame,
                fab_circle.width / 2, R.color.backgroundColor,
                object : ViewAnimUtils.OnRevealAnimationListener {
                    override fun onRevealHide() {
                        defaultBackPressed()
                    }

                    override fun onRevealShow() {

                    }
                })
        } else {
            defaultBackPressed()
        }
    }

}
