package com.cuong.haui.computershop.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.GioHangAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityCartBinding
import com.cuong.haui.computershop.model.EventBus.TinhTongEvent
import com.cuong.haui.computershop.utils.DefaultFirst1
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.DecimalFormat

class CartActivity : BaseActivity<ActivityCartBinding>() {
    var giohangtrong: TextView? = null
    var tongtien:TextView? = null
    var toolbar: Toolbar? = null
    var recyclerView: RecyclerView? = null
    var btnmuahang: Button? = null
    var adapter: GioHangAdapter? = null
    override fun initCreate() {
        initView()
        initControl()
        tinhTongTien()
    }
    private fun tinhTongTien() {
        var tongtiensp: Long = 0
        for (i in 0 until DefaultFirst1.manggiohang.size) {
            tongtiensp = tongtiensp + DefaultFirst1.manggiohang.get(i).getGiasp() * DefaultFirst1.manggiohang.get(i)
                .getSoluong()
        }
        val decimalFormat = DecimalFormat("###,###,###")
        tongtien!!.text = decimalFormat.format(tongtiensp)
    }

    private fun initControl() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        toolbar!!.setNavigationOnClickListener { finish() }
        recyclerView!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        if (DefaultFirst1.manggiohang.size === 0) {
            giohangtrong!!.visibility = View.VISIBLE
        } else {
            adapter = GioHangAdapter(applicationContext, DefaultFirst1.manggiohang)
            recyclerView!!.adapter = adapter
        }
    }

    private fun initView() {
        giohangtrong = findViewById(R.id.txtgiohangtrong)
        tongtien = findViewById(R.id.txttongtien)
        toolbar = findViewById(R.id.toobar)
        recyclerView = findViewById(R.id.recycleviewgiohang)
        btnmuahang = findViewById(R.id.btnmuahang)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun eventTinhTien(event: TinhTongEvent?) {
        //Toast.makeText(getApplicationContext(),event.toString(),Toast.LENGTH_LONG).show();
        if (event != null) {
            tinhTongTien()
        }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityCartBinding {
        return ActivityCartBinding.inflate(inflater)
    }

}