package com.cuong.haui.computershop.ui.orderManagement.frag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentDeliveredBinding


class DeliveredFragment : BaseFragment<FragmentDeliveredBinding>() {
    override fun initViewCreated() {

    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveredBinding {
        return FragmentDeliveredBinding.inflate(inflater)
    }

}