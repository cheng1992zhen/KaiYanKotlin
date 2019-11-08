package com.example.kotlindemo.base

import android.app.Application
import android.content.Context
import android.util.Log
import kotlin.properties.Delegates

class App : Application() {
    private val TAG = this::class.java.simpleName
    companion object {
        var context: Context by Delegates.notNull()
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Log.d(TAG, "应用启动...")
    }
}