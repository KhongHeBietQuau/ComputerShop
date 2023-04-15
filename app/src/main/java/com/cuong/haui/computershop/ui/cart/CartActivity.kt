package com.cuong.haui.computershop.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityCartBinding
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding

class CartActivity : BaseActivity<ActivityCartBinding>() {
    override fun initCreate() {

    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityCartBinding {
        return ActivityCartBinding.inflate(inflater)
    }

}