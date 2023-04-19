package com.cuong.haui.computershop.ui.cart

import android.app.ProgressDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.GioHangAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityCartBinding
import com.cuong.haui.computershop.model.EventBus.TinhTongEvent
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.DecimalFormat

class CartActivity : BaseActivity<ActivityCartBinding>() {
    private lateinit var database : DatabaseReference
    override fun initCreate() {
        initView()
        initControl()
        tinhTongTien()
        datHang()
    }

    private fun datHang() {
        binding.btnmuahang.setOnSafeClick {
            val progressDialog = ProgressDialog(this@CartActivity)
            progressDialog.setTitle("Cart")
            progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            for (i in 0 until DefaultFirst1.manggiohang.size) {
                DefaultFirst1.manggiohang.get(i).getGiasp() * DefaultFirst1.manggiohang.get(i).getSoluong()
                val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("SaleOrders")
                /*int sale_order_id, int idsp, String tensp, long giasp, String hinhsp, int soluong, int status,
                int admin_id, int user_id, int shipper_id, int status_pay, int warranty_period, String note,
                String payment_method, String receiver, String create_at, String update_at, String delivery_address,
                String phone_number*/
                val saleOrder = SaleOrder(1,DefaultFirst1.manggiohang.get(i).idsp,DefaultFirst1.manggiohang.get(i).tensp,
                    DefaultFirst1.manggiohang.get(i).giasp,DefaultFirst1.manggiohang.get(i).hinhsp,DefaultFirst1.manggiohang.get(i).soluong,
                1,0,DefaultFirst1.userCurrent.user_id,0,1,1,"1","1","1","1","1","1","1")


                usersRef.child("1").setValue(saleOrder)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            progressDialog.dismiss()
                            Toast.makeText(this,"Đặt hàng thành công", Toast.LENGTH_LONG).show()

                        }
                        else{
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }

    }

    private fun tinhTongTien() {
        var tongtiensp: Long = 0
        for (i in 0 until DefaultFirst1.manggiohang.size) {
            tongtiensp = tongtiensp + DefaultFirst1.manggiohang.get(i).getGiasp() * DefaultFirst1.manggiohang.get(i).getSoluong()
        }
        val decimalFormat = DecimalFormat("###,###,###")
        binding.txttongtien!!.text = decimalFormat.format(tongtiensp)
    }

    private fun initControl() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { finish() }
        binding.recycleviewgiohang!!.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recycleviewgiohang!!.layoutManager = layoutManager
        if (DefaultFirst1.manggiohang.size === 0) {
            binding.txtgiohangtrong!!.visibility = View.VISIBLE
        } else {
            var adapter = GioHangAdapter(applicationContext, DefaultFirst1.manggiohang)
            binding.recycleviewgiohang!!.adapter = adapter
        }
    }

    private fun initView() {

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