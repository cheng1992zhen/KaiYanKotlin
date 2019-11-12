package com.example.kotlindemo.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.FragmentTransaction
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.entity.TabEntity
import com.example.kotlindemo.ui.fragment.DiscoveryFragment
import com.example.kotlindemo.ui.fragment.HomeFragment
import com.example.kotlindemo.ui.fragment.HotFragment
import com.example.kotlindemo.ui.fragment.MineFragment
import com.example.kotlindemo.util.showToast
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : BaseActivity() {
    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(
        R.mipmap.ic_home_normal,
        R.mipmap.ic_discovery_normal,
        R.mipmap.ic_hot_normal,
        R.mipmap.ic_mine_normal
    )
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(
        R.mipmap.ic_home_selected,
        R.mipmap.ic_discovery_selected,
        R.mipmap.ic_hot_selected,
        R.mipmap.ic_mine_selected
    )

    //默认为0
    private var mIndex = 0

    override fun layoutId(): Int {
        return R.layout.activity_main
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        initTab()
        tab_layout.currentTab = mIndex
        switchFragment(mIndex)


    }

    private fun initTab() {
        //注意..和until的不同，前者为闭区间，后者为前闭后开。
        //        for (i in 0 until 10){
        //            println(i)
        //        }
        //      >>>0123456789
        //mapTo 为kotlin 对集合的一个操作符 https://juejin.im/post/5b1f7699f265da6e155d5965
        mTitles.indices.mapTo(mTabEntities) {
            TabEntity(
                mTitles[it],
                mIconSelectIds[it],
                mIconUnSelectIds[it]
            )
        }
        tab_layout.setTabData(mTabEntities)
        tab_layout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }
        })
    }

    override fun initView() {


    }

    override fun initData() {


    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mHomeFragment?.let { transaction.show(it) }
                ?: HomeFragment.getInstance(mTitles[position])
                    .let {
                        mHomeFragment = it
                        transaction.add(R.id.fl_container, it, "home")
                    }

            1 -> mDiscoveryFragment?.let { transaction.show(it) }
                ?: DiscoveryFragment.getInstance(
                    mTitles[position]
                ).let {
                    mDiscoveryFragment = it
                    transaction.add(R.id.fl_container, it, "discovery")
                }
            2 -> mHotFragment?.let { transaction.show(it) }
                ?: HotFragment.getInstance(mTitles[position]).let {
                    mHotFragment = it
                    transaction.add(R.id.fl_container, it, "hot")
                }
            3 -> mMineFragment?.let { transaction.show(it) }
                ?: MineFragment.getInstance(mTitles[position]).let {
                    mMineFragment = it
                    transaction.add(R.id.fl_container, it, "mine")
                }
        }
        mIndex = position
        tab_layout.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mHotFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (tab_layout != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
