package com.cuong.haui.computershop.ui.orderManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.ui.orderManagement.Fragment.ConfirmFragment
import com.cuong.haui.computershop.ui.orderManagement.Fragment.DeliveredFragment
import com.cuong.haui.computershop.ui.orderManagement.Fragment.DeliveringFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class OrderManagementActivity : AppCompatActivity() {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_management)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(ConfirmFragment())
    }
    private fun moveToFragment(fragment: Fragment){
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container,fragment)
        fragmentTrans.commit()
    }
}