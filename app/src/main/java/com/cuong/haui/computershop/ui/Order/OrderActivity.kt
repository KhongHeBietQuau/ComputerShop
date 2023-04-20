package com.cuong.haui.computershop.ui.Order

import android.app.ProgressDialog
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityOrderBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*

class OrderActivity : BaseActivity<ActivityOrderBinding>() {
    private lateinit var database : DatabaseReference
    var saleOrder_current_id : Int = 0
    override fun initCreate() {
        initData()
        getElementLast()
        ActionToolBar()
        datHang()
    }

    private fun initData() {
        binding.receiver.setText(DefaultFirst1.userCurrent.fullname.toString())
    }

    private fun getElementLast(){
        database = FirebaseDatabase.getInstance().getReference("SaleOrders")
        var databaseIndexLast: Query
        databaseIndexLast = database.limitToLast(1)
        var mangSaleOrder = ArrayList<SaleOrder>()
        databaseIndexLast.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val saleOrder = postSnapshot.getValue(SaleOrder::class.java)
                    if (saleOrder != null) {
                        mangSaleOrder.add(saleOrder)
                        saleOrder_current_id= saleOrder.sale_order_id+1
                        //Toast.makeText(applicationContext, saleOrder_current_id.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
    private fun datHang() {
        binding.btnDatHang.setOnSafeClick {
        val deliveryAddress = binding.deliveryAddress.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val receiver = binding.receiver.text.toString()

        when{
            TextUtils.isEmpty(deliveryAddress) -> Toast.makeText(this,"Vui lòng nhập địa chỉ nhận hàng", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(phoneNumber) -> Toast.makeText(this,"vui lòng nhập số điện thoại", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(receiver) -> Toast.makeText(this,"Vui lòng nhập tên người nhận", Toast.LENGTH_LONG).show()

            else ->{
                DefaultFirst1.saleOrderCurrent.setStatus(1)
                DefaultFirst1.saleOrderCurrent.setAdmin_id(0)
                DefaultFirst1.saleOrderCurrent.setUser_id(DefaultFirst1.userCurrent.user_id)
                DefaultFirst1.saleOrderCurrent.setShipper_id(0)
                DefaultFirst1.saleOrderCurrent.setStatus_pay(1)
                DefaultFirst1.saleOrderCurrent.setWarranty_period(0)
                DefaultFirst1.saleOrderCurrent.setNote(binding.note.text.toString())
                var payment_method =1
                if(binding.onlinePayment.isChecked)
                    payment_method = 2
                DefaultFirst1.saleOrderCurrent.setPayment_method(payment_method.toString())
                DefaultFirst1.saleOrderCurrent.setReceiver(binding.receiver.text.toString())
                DefaultFirst1.saleOrderCurrent.setCreate_at("")
                DefaultFirst1.saleOrderCurrent.setUpdate_at("")
                DefaultFirst1.saleOrderCurrent.setDelivery_address(binding.deliveryAddress.text.toString())
                DefaultFirst1.saleOrderCurrent.setPhone_number(binding.phoneNumber.text.toString())

                if(payment_method == 1){
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Order")
                    progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()
                    for (i in 0 until DefaultFirst1.manggiohang.size) {
                        DefaultFirst1.manggiohang.get(i).getGiasp() * DefaultFirst1.manggiohang.get(i).getSoluong()
                        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("SaleOrders")

                        DefaultFirst1.saleOrderCurrent.setIdsp(DefaultFirst1.manggiohang.get(i).idsp)
                        DefaultFirst1.saleOrderCurrent.setGiasp(DefaultFirst1.manggiohang.get(i).giasp)
                        DefaultFirst1.saleOrderCurrent.setTensp(DefaultFirst1.manggiohang.get(i).tensp)
                        DefaultFirst1.saleOrderCurrent.setHinhsp(DefaultFirst1.manggiohang.get(i).hinhsp)
                        DefaultFirst1.saleOrderCurrent.setSoluong(DefaultFirst1.manggiohang.get(i).soluong)
                        DefaultFirst1.saleOrderCurrent.setSale_order_id(saleOrder_current_id+i)


                        usersRef.child((saleOrder_current_id+i).toString()).setValue(DefaultFirst1.saleOrderCurrent)
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful){
                                    progressDialog.dismiss()
                                    Toast.makeText(this,"Đặt hàng thành công", Toast.LENGTH_LONG).show()
                                    DefaultFirst1.manggiohang.clear()
                                    //openActivity(MainActivity::class.java,true)

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
                else{

                }
            }
        }
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityOrderBinding {
        return ActivityOrderBinding.inflate(inflater)
    }
    private fun ActionToolBar() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { finish() }
    }

}