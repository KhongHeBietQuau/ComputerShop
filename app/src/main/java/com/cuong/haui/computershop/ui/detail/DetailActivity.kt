package com.cuong.haui.computershop.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityCartBinding
import com.cuong.haui.computershop.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity<ActivityDetailBinding>() {
    override fun initCreate() {

    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(inflater)
    }
}