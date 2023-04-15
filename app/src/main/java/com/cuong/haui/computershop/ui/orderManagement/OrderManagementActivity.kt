package com.cuong.haui.computershop.ui.orderManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityOrderManagementBinding
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding
import com.cuong.haui.computershop.ui.orderManagement.frag.ConfirmFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveredFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveringFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderManagementActivity : BaseActivity<ActivityOrderManagementBinding>() {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.time_left -> {
                moveToFragment(ConfirmFragment())

                return@OnNavigationItemSelectedListener  true
            }
            R.id.deliverying -> {
                moveToFragment(DeliveringFragment())

                return@OnNavigationItemSelectedListener  true

            }
            R.id.deliveryed -> {

                moveToFragment(DeliveredFragment())
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
        setContentView(R.layout.activity_order_management)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ConfirmFragment())
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityOrderManagementBinding {
        return ActivityOrderManagementBinding.inflate(inflater)
    }
}