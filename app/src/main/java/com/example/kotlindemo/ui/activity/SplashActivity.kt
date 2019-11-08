package com.example.kotlindemo.ui.activity

import android.Manifest
import android.content.Intent
import android.graphics.Typeface
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.example.kotlindemo.base.App
import com.example.kotlindemo.R
import com.example.kotlindemo.base.BaseActivity
import com.example.kotlindemo.util.AppUtils
import kotlinx.android.synthetic.main.activity_splash.*
import pub.devrel.easypermissions.EasyPermissions

class SplashActivity : BaseActivity() {
    private var textTypeFace: Typeface? = null

    private var descTypeFace: Typeface? = null

    private var alphaAnimation: AlphaAnimation? = null

    init {
        textTypeFace = Typeface.createFromAsset(App.context.assets, "fonts/Lobster-1.4.otf")
        descTypeFace =
            Typeface.createFromAsset(App.context.assets, "fonts/FZLanTingHeiS-L-GB-Regular.TTF")
    }

    override fun layoutId(): Int = R.layout.activity_splash

    override fun initView() {
        super.initView()
        tv_app_name.typeface = textTypeFace
        tv_splash_desc.typeface = descTypeFace
        tv_version_name.text = "v${AppUtils.getVerName(this)}"
        alphaAnimation = AlphaAnimation(0.3f, 1f)
        alphaAnimation?.duration = 2000
        //匿名内部类写法
        alphaAnimation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                goToMain()
            }

            override fun onAnimationStart(animation: Animation?) {

            }
        })
        checkPermission()
    }

    override fun initData() {
        super.initData()
    }

    override fun start() {
        super.start()
    }

    fun goToMain() {
        var intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkPermission() {
        val perms = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        EasyPermissions.requestPermissions(this, "KotlinMvp应用需要以下权限，请允许", 0, *perms)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        super.onPermissionsGranted(requestCode, perms)
        if (0 == requestCode) {
            if (perms.isNotEmpty()) {
                if (perms.contains(
                        Manifest.permission.READ_PHONE_STATE
                    ) &&
                    perms.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ) {
                    if (alphaAnimation != null) {
                        iv_web_icon.startAnimation(alphaAnimation)
                    }
                }
            }
        }
    }
}