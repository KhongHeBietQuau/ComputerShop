package com.cuong.haui.computershop.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.BuildConfig
import androidx.viewbinding.ViewBinding
import com.cuong.haui.computershop.utils.Constant

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected lateinit var binding: VB

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        _binding = inflateViewBinding(layoutInflater)
        binding = inflateViewBinding(layoutInflater)
        setContentView(binding.root)
        //lifecycle.addObserver(this)
        lifecycleScope.launchWhenResumed {
            if (BuildConfig.DEBUG) {
                println("${Constant.TAG} SCREEN_APP ${this@BaseActivity::class.java.name}")
            }
        }
        initCreate()
    }

    abstract fun initCreate()


    /**override it and inflate your view binding, demo in MainActivity*/
    abstract fun inflateViewBinding(inflater: LayoutInflater): VB

    override fun onDestroy() {
        _binding = null
        //lifecycle.removeObserver(this)
        super.onDestroy()
    }

}