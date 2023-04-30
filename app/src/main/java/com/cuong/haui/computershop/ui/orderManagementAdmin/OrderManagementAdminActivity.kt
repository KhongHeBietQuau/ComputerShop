package com.cuong.haui.computershop.ui.orderManagementAdmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityOrderManagementAdminBinding
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.orderManagement.frag.ConfirmFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveredFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveringFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.ProductReturnFragment
import com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin.ConfirmAdminFragment
import com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin.DeliveredAdminFragment
import com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin.DeliveringAdminFragment
import com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin.ProductReturnAdminFragment
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.String

class OrderManagementAdminActivity : BaseActivity<ActivityOrderManagementAdminBinding>() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.time_left -> {
                moveToFragment(ConfirmAdminFragment())

                return@OnNavigationItemSelectedListener  true
            }
            R.id.deliverying -> {
                moveToFragment(DeliveringAdminFragment())

                return@OnNavigationItemSelectedListener  true

            }
            R.id.deliveryed -> {

                moveToFragment(DeliveredAdminFragment())
                return@OnNavigationItemSelectedListener  true

            }
            R.id.product_return -> {

                moveToFragment(ProductReturnAdminFragment())
                return@OnNavigationItemSelectedListener  true

            }

        }

        false
    }
    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
    override fun initCreate() {
        initView()
        ActionToolBar()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ConfirmAdminFragment())
    }
    private fun initView() {


    }
    private fun ActionToolBar() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { finish() }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityOrderManagementAdminBinding {
        return ActivityOrderManagementAdminBinding.inflate(inflater)
    }

}