package com.example.kotlindemo.base

import io.reactivex.disposables.CompositeDisposable

open class BasePresenter<T : IView> : IPresenter<T> {

    var mRootView: T? = null
        private set
    private var compositeDisposable = CompositeDisposable()

    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    private val isViewAttached: Boolean get() = mRootView != null

    fun checkViewAttached() {
        if (!isViewAttached) {
            throw MvpViewNotAttachedException()
        }
    }

    override fun detachView() {
        mRootView = null
        //保证activity结束时取消所有正在执行的订阅
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.clear()
        }

    }
}

private class MvpViewNotAttachedException internal constructor() :
    RuntimeException("Please call IPresenter.attachView(IBaseView) before" + " requesting data to the IPresenter")


