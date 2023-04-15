package com.cuong.haui.computershop.ui.orderManagement.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentConfirmBinding


class ConfirmFragment : BaseFragment<FragmentConfirmBinding>() {

    override fun initViewCreated() {

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmBinding {
        return FragmentConfirmBinding.inflate(inflater)
    }

}