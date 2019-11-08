package com.example.kotlindemo.base

interface IActivity {
    abstract fun layoutId(): Int
    fun initData()
    fun initView()
    fun start()
}