package com.cuong.haui.computershop.ui.warrantyManagement

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityWarrantyManagementBinding
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.orderManagement.frag.ConfirmFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveredFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.DeliveringFragment
import com.cuong.haui.computershop.ui.orderManagement.frag.ProductReturnFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.CancelWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.ConfirmWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.ConfirmedWarrantyFragment
import com.cuong.haui.computershop.ui.warrantyManagement.frag.SuccessfulWarrantyFragment
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.String

class warrantyManagementActivity : BaseActivity<ActivityWarrantyManagementBinding>()  {
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.confirm -> {
                moveToFragment(ConfirmWarrantyFragment())

                return@OnNavigationItemSelectedListener  true
            }
            R.id.confirmed -> {
                moveToFragment(ConfirmedWarrantyFragment())

                return@OnNavigationItemSelectedListener  true

            }
            R.id.successful_warranty -> {

                moveToFragment(SuccessfulWarrantyFragment())
                return@OnNavigationItemSelectedListener  true

            }
            R.id.cancel_warranty -> {

                moveToFragment(CancelWarrantyFragment())
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
        moveToFragment(ConfirmWarrantyFragment())
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
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityWarrantyManagementBinding {
        return ActivityWarrantyManagementBinding.inflate(inflater)
    }

}