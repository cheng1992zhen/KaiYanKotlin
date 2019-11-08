package com.example.kotlindemo.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlindemo.util.showToast
import com.example.kotlindemo.widget.MultipleStatusView
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity : AppCompatActivity(), IActivity, EasyPermissions.PermissionCallbacks {
    /**
     * 多种状态的 View 的切换
     */
    protected var mLayoutStatusView: MultipleStatusView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId())
        initData()
        initView()
        start()
        initListener()
    }

    private fun initListener() {
        mLayoutStatusView?.setOnClickListener(mRetryClickListener)
    }

    open val mRetryClickListener: View.OnClickListener = View.OnClickListener { start() }
    abstract override fun layoutId(): Int


    override fun initData() {

    }

    override fun initView() {
    }

    override fun start() {
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        var sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            showToast("已拒绝权限" + sb + "并不再询问")
            AppSettingsDialog.Builder(this)
                .setRationale("此功能需要" + sb + "权限，否则无法正常使用，是否打开设置")
                .setNegativeButton("不行")
                .setPositiveButton("好")
                .build()
                .show()

        }
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d("EasyPermissions", "获取到的权限$perms")
    }


}