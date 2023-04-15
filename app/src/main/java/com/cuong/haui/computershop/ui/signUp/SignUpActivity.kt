package com.cuong.haui.computershop.ui.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignInBinding
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun initCreate() {

    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(inflater)
    }

}