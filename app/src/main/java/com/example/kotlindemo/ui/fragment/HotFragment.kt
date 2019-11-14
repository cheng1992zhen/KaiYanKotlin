package com.example.kotlindemo.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseFragment
import com.example.kotlindemo.base.BaseFragmentAdapter
import com.example.kotlindemo.entity.TabInfoBean
import com.example.kotlindemo.mvp.contract.HotTabContract
import com.example.kotlindemo.mvp.presenter.HotTabPresenter
import com.example.kotlindemo.util.StatusBarUtil
import com.example.kotlindemo.util.showToast
import com.hazz.kotlinmvp.net.exception.ErrorStatus
import kotlinx.android.synthetic.main.fragment_hot.*

class HotFragment : BaseFragment() ,HotTabContract.View{

    private val mPresenter by lazy { HotTabPresenter() }

    private var mTitle: String? = null

    /**
     * 存放 tab 标题
     */
    private val mTabTitleList = ArrayList<String>()

    private val mFragmentList = ArrayList<Fragment>()
    override fun getLayoutId(): Int = R.layout.fragment_hot

    override fun initView() {
        //状态栏透明和间距处理
        activity?.let { StatusBarUtil.darkMode(it) }
        activity?.let { StatusBarUtil.setPaddingSmart(it, toolbar) }
    }
    init {
        mPresenter.attachView(this)
    }

    override fun lazyLoad() {
        mPresenter.getTabInfo()
    }

    companion object {
        fun getInstance(title: String): HotFragment {
            val hotFragment = HotFragment()
            hotFragment.arguments = Bundle()
            hotFragment.mTitle = title
            return hotFragment
        }
    }



    override fun setTabInfo(tabInfoBean: TabInfoBean) {
        multipleStatusView.showContent()

        tabInfoBean.tabInfo.tabList.mapTo(mTabTitleList) { it.name }
        tabInfoBean.tabInfo.tabList.mapTo(mFragmentList) { RankFragment.getInstance(it.apiUrl) }

        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager,mFragmentList,mTabTitleList)
        mTabLayout.setupWithViewPager(mViewPager)
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

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }
}