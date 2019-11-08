package com.example.kotlindemo

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App : Application() {

    companion object {

        private val TAG = "MyApplication"

        var context: Context by Delegates.notNull()
            private set

    }
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}