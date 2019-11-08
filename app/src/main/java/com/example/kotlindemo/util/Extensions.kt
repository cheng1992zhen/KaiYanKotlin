package com.example.kotlindemo.util

import android.content.Context
import android.widget.Toast

/**
 * 拓展方法
 */
fun Context.showToast(content: String): Toast {
    var toast = Toast.makeText(this, content, Toast.LENGTH_SHORT)
    toast.show()
    return toast
}

