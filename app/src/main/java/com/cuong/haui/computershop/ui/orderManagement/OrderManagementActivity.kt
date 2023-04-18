package com.cuong.haui.computershop.ui.orderManagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityOrderManagementBinding
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.orderManagement.frag.ConfirmFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveredFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveringFragment
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.String

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
        initView()
        ActionToolBar()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ConfirmFragment())
    }
    private fun initView() {

        if (DefaultFirst1.manggiohang != null) {
            binding.menuSl.setText(String.valueOf(DefaultFirst1.manggiohang.size))
        }

        binding.framegiohang.setOnClickListener {
            val giohang = Intent(applicationContext, CartActivity::class.java)
            startActivity(giohang)
        }
    }
    private fun ActionToolBar() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { openActivity(MainActivity::class.java,true) }
    }
    override fun onResume() {
        super.onResume()
        var totalItem = 0

        for (i in 0 until DefaultFirst1.manggiohang.size) {
            totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
        }

        binding.menuSl!!.setText(totalItem.toString())

    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityOrderManagementBinding {
        return ActivityOrderManagementBinding.inflate(inflater)
    }
}