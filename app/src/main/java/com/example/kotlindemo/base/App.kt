package com.example.kotlindemo.base

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDexApplication
import com.example.kotlindemo.util.DisplayManager
import kotlin.properties.Delegates

class App : MultiDexApplication() {
    private val TAG = this::class.java.simpleName
    companion object {
        var context: Context by Delegates.notNull()
            private set

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        DisplayManager.init(this)
        Log.d(TAG, "应用启动...")
    }
}