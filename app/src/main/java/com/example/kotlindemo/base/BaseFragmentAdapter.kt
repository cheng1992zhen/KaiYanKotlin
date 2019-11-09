package com.example.kotlindemo.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.ArrayList

class BaseFragmentAdapter : FragmentPagerAdapter {

    private var mFragmentList: List<Fragment>? = ArrayList()
    private var mTitles: List<String>? = null

    constructor(fm: FragmentManager, fragmentList: List<Fragment>) : super(fm) {
        this.mFragmentList = fragmentList
    }

    constructor(
        fm: FragmentManager,
        fragmentList: List<Fragment>,
        mTitles: List<String>
    ) : super(fm) {
        this.mTitles = mTitles
        setFragments(fm, fragmentList, mTitles)
    }

    private fun setFragments(fm: FragmentManager, fragments: List<Fragment>, mTitles: List<String>) {
        this.mTitles = mTitles
        if (this.mFragmentList != null) {
            val transaction = fm.beginTransaction()
            mFragmentList!!.forEach {
                transaction.remove(it)
            }
            transaction.commitAllowingStateLoss()
            fm.executePendingTransactions()
        }
        this.mFragmentList = fragments
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (null != mTitles) mTitles!![position] else ""
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList!![position]
    }

    override fun getCount(): Int {
        return mFragmentList!!.size
    }
}