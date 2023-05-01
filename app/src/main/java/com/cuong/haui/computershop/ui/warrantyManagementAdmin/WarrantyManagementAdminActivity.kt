package com.cuong.haui.computershop.ui.warrantyManagementAdmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityWarrantyManagementAdminBinding
import com.cuong.haui.computershop.databinding.ActivityWarrantyManagementBinding
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.warrantyManagement.frag.CancelWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.ConfirmWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.ConfirmedWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.SuccessfulWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagementAdmin.fragAdmin.CancelWarrantyAdminFragment
import com.cuong.haui.computershop.ui.warrantyManagementAdmin.fragAdmin.ComfirmedWarrantyAdminFragment
import com.cuong.haui.computershop.ui.warrantyManagementAdmin.fragAdmin.ConfirmWarrantyAdminFragment
import com.cuong.haui.computershop.ui.warrantyManagementAdmin.fragAdmin.SuccessfulWarrantyAdminFragment
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.String

class WarrantyManagementAdminActivity : BaseActivity<ActivityWarrantyManagementAdminBinding>() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.confirm -> {
                moveToFragment(ConfirmWarrantyAdminFragment())

                return@OnNavigationItemSelectedListener  true
            }
            R.id.confirmed -> {
                moveToFragment(ComfirmedWarrantyAdminFragment())

                return@OnNavigationItemSelectedListener  true

            }
            R.id.successful_warranty -> {

                moveToFragment(SuccessfulWarrantyAdminFragment())
                return@OnNavigationItemSelectedListener  true

            }
            R.id.cancel_warranty -> {

                moveToFragment(CancelWarrantyAdminFragment())
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

        val navView: BottomNavigationView = binding.navView
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ConfirmWarrantyAdminFragment())
    }
    private fun initView() {


    }
    private fun ActionToolBar() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { finish() }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityWarrantyManagementAdminBinding {
        return ActivityWarrantyManagementAdminBinding.inflate(inflater)
    }

}