package com.example.kotlindemo.base

interface IPresenter<in V : IView> {

    fun attachView(mRootView: V)

    fun detachView()
}