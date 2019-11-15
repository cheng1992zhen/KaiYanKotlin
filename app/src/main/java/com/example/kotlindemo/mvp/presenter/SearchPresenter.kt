package com.example.kotlindemo.mvp.presenter

import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.base.IPresenter
import com.example.kotlindemo.mvp.contract.SearchContract
import com.example.kotlindemo.mvp.model.SearchModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {
    private var nextPageUrl: String? = null
    private val mModel by lazy { SearchModel() }
    /**
     * 获取热门关键词
     */
    override fun requestHotWordData() {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        val subscribe = mModel.requestHotWordData()
            .subscribe({ string ->
                mRootView?.apply {
                    setHotWordData(string)
                }

            }, { throwable ->
                mRootView?.apply {
                    //处理异常
                    showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                }
            })
        addSubscription(subscribe)

    }

    /**
     * 查询关键词
     */
    override fun querySearchData(words: String) {
        checkViewAttached()
        mRootView?.apply {
            closeSoftKeyboard()
            showLoading()
        }
        addSubscription(
            disposable = mModel.getSearchResult(words)
                .subscribe({ issue ->
                    mRootView?.apply {
                        dismissLoading()
                        if (issue.count > 0 && issue.itemList.size > 0) {
                            nextPageUrl = issue.nextPageUrl
                            setSearchResult(issue)
                        } else
                            setEmptyView()
                    }
                }, { throwable ->
                    mRootView?.apply {
                        dismissLoading()
                        //处理异常
                        showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }
                })
        )
    }

    override fun loadMoreData() {
        checkViewAttached()
        nextPageUrl?.let {
            addSubscription(disposable = mModel.loadMoreData(it)
                .subscribe({ issue ->
                    mRootView?.apply {
                        nextPageUrl = issue.nextPageUrl
                        setSearchResult(issue)
                    }
                }, { throwable ->
                    mRootView?.apply {
                        //处理异常
                        showError(
                            ExceptionHandle.handleException(throwable),
                            ExceptionHandle.errorCode
                        )
                    }
                })
            )
        }
    }
}