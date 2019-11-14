package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.HomeContract
import com.example.kotlindemo.mvp.model.HomeModel
import com.example.kotlindemo.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = null     //加载首页的Banner 数据+一页数据合并后，nextPageUrl没 add
    private val homeModel: HomeModel by lazy { HomeModel() }


    override fun requestHomeData(num: Int) {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = homeModel.requestHomeData(num)
            .flatMap { homeBean ->
                var bannerItemList = homeBean.issueList[0].itemList
                bannerItemList.filter { item ->
                    //filter 过滤
                    item.type == "banner2" || item.type == "horizontalScrollCard"

                }.forEach { item ->
                    //forEach 普通遍历集合方式
                    bannerItemList.remove(item)
                }
                bannerHomeBean = homeBean
                homeModel.loadMoreData(homeBean.nextPageUrl)
            }
            .subscribe({ homeBean ->
                mRootView?.apply {
                    dismissLoading()
                    nextPageUrl = homeBean.nextPageUrl
                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newBannerItemList = homeBean.issueList[0].itemList

                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        newBannerItemList.remove(item)
                    }
                    // 重新赋值 Banner 长度
                    bannerHomeBean!!.issueList[0].count =
                        bannerHomeBean!!.issueList[0].itemList.size

                    //赋值过滤后的数据 + banner 数据
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                    setHomeData(bannerHomeBean!!)
                }

            }, { t ->
                mRootView?.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }

            })

        addSubscription(disposable)
    }

    override fun loadMoreData() {
        val disposable = nextPageUrl?.let {
            homeModel.loadMoreData(it)
                .subscribe({ homeBean ->
                    mRootView?.apply {
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newItemList = homeBean.issueList[0].itemList

                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newItemList.remove(item)
                        }

                        nextPageUrl = homeBean.nextPageUrl
                        setMoreData(newItemList)
                    }

                }, { t ->
                    mRootView?.apply {
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })

        }
        if (disposable != null) {
            addSubscription(disposable)
        }
    }
}



