package com.example.kotlindemo.mvp.presenter


import com.example.kotlindemo.base.BasePresenter
import com.example.kotlindemo.mvp.contract.HotTabContract
import com.example.kotlindemo.mvp.model.HotTabModel
import com.hazz.kotlinmvp.net.exception.ExceptionHandle

/**
 * Created by xuhao on 2017/11/30.
 * desc: 获取 TabInfo Presenter
 */
class HotTabPresenter: BasePresenter<HotTabContract.View>(),HotTabContract.Presenter {

    private val hotTabModel by lazy { HotTabModel() }


    override fun getTabInfo() {
        checkViewAttached()
        mRootView?.showLoading()
        val disposable = hotTabModel.getTabInfo()
                .subscribe({
                    tabInfo->
                    mRootView?.setTabInfo(tabInfo)
                },{
                    throwable->
                    //处理异常
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode)
                })
        addSubscription(disposable)
    }
}