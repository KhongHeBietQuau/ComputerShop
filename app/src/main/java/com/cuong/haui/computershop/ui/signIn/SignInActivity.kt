package com.cuong.haui.computershop.ui.signIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignInBinding

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySignInBinding { return ActivitySignInBinding.inflate(inflater) }



    override fun initCreate() {
        initListener()
    }



    private fun initListener() {
        binding.btndangnhap.setOnClickListener {

        }
    }

}