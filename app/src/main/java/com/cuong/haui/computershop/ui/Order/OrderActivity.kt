package com.cuong.haui.computershop.ui.Order

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityOrderBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.payZaloPay.ZaloPayActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class OrderActivity : BaseActivity<ActivityOrderBinding>() {
    private lateinit var database : DatabaseReference
    var flag = 0
    var checkWarehouse : Int =1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initCreate() {
        initData()
        getElementLast()
        ActionToolBar()
        if(DefaultFirst1.manggiohang.size>0) {

            datHang()
        }
        else{
            Toast.makeText(
                this,
                "Giỏ hàng trống không thể đặt hàng",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun initData() {
        binding.receiver.setText(DefaultFirst1.userCurrent.fullname.toString())
        checkWarehouse =1
        for (i in 0 until DefaultFirst1.manggiohang.size) {

            // kiem tra so luong tung san pham
            var myRef = FirebaseDatabase.getInstance().getReference("Products").child(DefaultFirst1.manggiohang.get(i).idsp.toString())

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    var sanPhamMoi = dataSnapshot.getValue(SanPhamMoi::class.java)
                    if (sanPhamMoi != null)
                    if(sanPhamMoi?.current_quantity!! < DefaultFirst1.manggiohang.get(i).soluong){
                        checkWarehouse =0
                    }
                    //Toast.makeText(applicationContext, DefaultFirst1.userCurrent.toString() , Toast.LENGTH_LONG).show()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })
        }
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
                        DefaultFirst1.saleOrder_current_id= saleOrder.sale_order_id+1
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun datHang() {
        if (DefaultFirst1.status_pay_zalopay == 1) {
            Toast.makeText(this, "Chuyển khoản thành công", Toast.LENGTH_LONG)
                .show()
            // thanh toan oline khi nguoi dung chuyen tien thanh cong
            DefaultFirst1.saleOrderCurrent.setStatus("2")
            DefaultFirst1.saleOrderCurrent.setStatus_pay(2)
            DefaultFirst1.saleOrderCurrent.setPayment_method("2")
            var soluongCo: Int = 0
            var soLuongDat: Int = 0
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Order")
            progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
            val sanPhamMoiReal =
                SanPhamMoi(0, "0", 0, 0, "", "", "", "", "", "", "", 0, "", "", 0, 0, 0, 0)
            for (i in 0 until DefaultFirst1.manggiohang.size) {
                var flagCheck: Int = 0
                // lấy số lượng hiện tại
                var myRef = FirebaseDatabase.getInstance().getReference("Products")
                    .child(DefaultFirst1.manggiohang.get(i).idsp.toString())

                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        var sanPhamMoi = dataSnapshot.getValue(SanPhamMoi::class.java)!!
                        if (sanPhamMoi != null) {
                            sanPhamMoiReal.current_quantity = sanPhamMoi.current_quantity
                            var ans =
                                (sanPhamMoiReal.current_quantity - DefaultFirst1.manggiohang.get(i).soluong)
                            soluongCo = sanPhamMoiReal.current_quantity
                            if (flagCheck == 0) {
                                flagCheck = 1
                                soLuongDat = DefaultFirst1.manggiohang.get(i).soluong
                                //Toast.makeText(applicationContext,soluongCo.toString() + "  " +  soLuongDat.toString() , Toast.LENGTH_LONG).show()
                                if (sanPhamMoiReal != null) {
                                    val mDbRefSet = FirebaseDatabase.getInstance()
                                        .getReference("Products/" + DefaultFirst1.manggiohang.get(i).idsp.toString() + "/current_quantity")
                                    var ans =
                                        (sanPhamMoiReal.current_quantity - DefaultFirst1.manggiohang.get(
                                            i
                                        ).soluong)
                                    mDbRefSet.setValue(soluongCo - soLuongDat)
                                        .addOnSuccessListener {
                                        }
                                        .addOnFailureListener {
                                            // Write failed
                                            // ...
                                        }

                                }
                            }

                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Getting Post failed, log a message
                        Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                        // ...
                    }
                })

                val usersRef: DatabaseReference =
                    FirebaseDatabase.getInstance().reference.child("SaleOrders")

                DefaultFirst1.saleOrderCurrent.setIdsp(DefaultFirst1.manggiohang.get(i).idsp)
                DefaultFirst1.saleOrderCurrent.setGiasp(DefaultFirst1.manggiohang.get(i).giasp)
                DefaultFirst1.saleOrderCurrent.setTensp(DefaultFirst1.manggiohang.get(i).tensp)
                DefaultFirst1.saleOrderCurrent.setHinhsp(DefaultFirst1.manggiohang.get(i).hinhsp)
                DefaultFirst1.saleOrderCurrent.setSoluong(
                    DefaultFirst1.manggiohang.get(
                        i
                    ).soluong
                )
                DefaultFirst1.saleOrderCurrent.setSale_order_id(DefaultFirst1.saleOrder_current_id + i)


                usersRef.child((DefaultFirst1.saleOrder_current_id + i).toString())
                    .setValue(DefaultFirst1.saleOrderCurrent)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss()
                            if(flag == 0) {
                                Toast.makeText(
                                    this,
                                    "Đặt hàng thành công",
                                    Toast.LENGTH_LONG
                                ).show()
                                flag =1
                            }
                            DefaultFirst1.manggiohang.clear()
                            //openActivity(MainActivity::class.java,true)

                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG)
                                .show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
            progressDialog.dismiss()
            DefaultFirst1.status_pay_zalopay = 0
            // het thanh toan oline
        }
        else{
        binding.btnDatHang.setOnSafeClick {
            //kiem tra con hang ko
            val positiveButtonClick = { dialog: DialogInterface, which: Int ->
                //
                val deliveryAddress = binding.deliveryAddress.text.toString()
                val phoneNumber = binding.phoneNumber.text.toString()
                val receiver = binding.receiver.text.toString()

                when {
                    TextUtils.isEmpty(deliveryAddress) -> Toast.makeText(
                        this,
                        "Vui lòng nhập địa chỉ nhận hàng",
                        Toast.LENGTH_LONG
                    ).show()
                    TextUtils.isEmpty(phoneNumber) -> Toast.makeText(
                        this,
                        "vui lòng nhập số điện thoại",
                        Toast.LENGTH_LONG
                    ).show()
                    TextUtils.isEmpty(receiver) -> Toast.makeText(
                        this,
                        "Vui lòng nhập tên người nhận",
                        Toast.LENGTH_LONG
                    ).show()

                    else -> {

                        DefaultFirst1.saleOrderCurrent.setStatus("1")
                        DefaultFirst1.saleOrderCurrent.setAdmin_id(0)
                        DefaultFirst1.saleOrderCurrent.setUser_id(DefaultFirst1.userCurrent.user_id)
                        DefaultFirst1.saleOrderCurrent.setShipper_id(0)
                        DefaultFirst1.saleOrderCurrent.setStatus_pay(1)
                        DefaultFirst1.saleOrderCurrent.setWarranty_period(0)
                        DefaultFirst1.saleOrderCurrent.setNote(binding.note.text.toString())
                        var payment_method = 1
                        if (binding.onlinePayment.isChecked) {
                            payment_method = 2
                        }
                        DefaultFirst1.saleOrderCurrent.setPayment_method(payment_method.toString())
                        DefaultFirst1.saleOrderCurrent.setReceiver(binding.receiver.text.toString())
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val current = LocalDateTime.now().format(formatter)
                        DefaultFirst1.saleOrderCurrent.setCreate_at(current)
                        DefaultFirst1.saleOrderCurrent.setUpdate_at(current)
                        DefaultFirst1.saleOrderCurrent.setDelivery_address(binding.deliveryAddress.text.toString())
                        DefaultFirst1.saleOrderCurrent.setPhone_number(binding.phoneNumber.text.toString())

                        if (checkWarehouse == 1) {

                            if (payment_method == 1) {
                                // thanh toan truc tiep
                                var soluongCo: Int = 0
                                var soLuongDat: Int = 0
                                val progressDialog = ProgressDialog(this)
                                progressDialog.setTitle("Order")
                                progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                                progressDialog.setCanceledOnTouchOutside(false)
                                progressDialog.show()
                                val sanPhamMoiReal = SanPhamMoi(
                                    0,
                                    "0",
                                    0,
                                    0,
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    "",
                                    0,
                                    "",
                                    "",
                                    0,
                                    0,
                                    0,
                                    0
                                )
                                for (i in 0 until DefaultFirst1.manggiohang.size) {
                                    var flagCheck: Int = 0
                                    // lấy số lượng hiện tại
                                    var myRef =
                                        FirebaseDatabase.getInstance().getReference("Products")
                                            .child(DefaultFirst1.manggiohang.get(i).idsp.toString())

                                    myRef.addValueEventListener(object : ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                                            var sanPhamMoi =
                                                dataSnapshot.getValue(SanPhamMoi::class.java)!!
                                            if (sanPhamMoi != null) {
                                                sanPhamMoiReal.current_quantity =
                                                    sanPhamMoi.current_quantity
                                                var ans =
                                                    (sanPhamMoiReal.current_quantity - DefaultFirst1.manggiohang.get(
                                                        i
                                                    ).soluong)
                                                soluongCo = sanPhamMoiReal.current_quantity
                                                if (flagCheck == 0) {
                                                    flagCheck = 1
                                                    soLuongDat =
                                                        DefaultFirst1.manggiohang.get(i).soluong
                                                    //Toast.makeText(applicationContext,soluongCo.toString() + "  " +  soLuongDat.toString() , Toast.LENGTH_LONG).show()
                                                    if (sanPhamMoiReal != null) {
                                                        val mDbRefSet =
                                                            FirebaseDatabase.getInstance()
                                                                .getReference(
                                                                    "Products/" + DefaultFirst1.manggiohang.get(
                                                                        i
                                                                    ).idsp.toString() + "/current_quantity"
                                                                )
                                                        var ans =
                                                            (sanPhamMoiReal.current_quantity - DefaultFirst1.manggiohang.get(
                                                                i
                                                            ).soluong)
                                                        mDbRefSet.setValue(soluongCo - soLuongDat)
                                                            .addOnSuccessListener {
                                                            }
                                                            .addOnFailureListener {
                                                                // Write failed
                                                                // ...
                                                            }

                                                    }
                                                }

                                            }
                                        }

                                        override fun onCancelled(databaseError: DatabaseError) {
                                            // Getting Post failed, log a message
                                            Log.w(
                                                "abc",
                                                "loadPost:onCancelled",
                                                databaseError.toException()
                                            )
                                            // ...
                                        }
                                    })

                                    val usersRef: DatabaseReference =
                                        FirebaseDatabase.getInstance().reference.child("SaleOrders")

                                    DefaultFirst1.saleOrderCurrent.setIdsp(
                                        DefaultFirst1.manggiohang.get(
                                            i
                                        ).idsp
                                    )
                                    DefaultFirst1.saleOrderCurrent.setGiasp(
                                        DefaultFirst1.manggiohang.get(
                                            i
                                        ).giasp
                                    )
                                    DefaultFirst1.saleOrderCurrent.setTensp(
                                        DefaultFirst1.manggiohang.get(
                                            i
                                        ).tensp
                                    )
                                    DefaultFirst1.saleOrderCurrent.setHinhsp(
                                        DefaultFirst1.manggiohang.get(
                                            i
                                        ).hinhsp
                                    )
                                    DefaultFirst1.saleOrderCurrent.setSoluong(
                                        DefaultFirst1.manggiohang.get(
                                            i
                                        ).soluong
                                    )
                                    DefaultFirst1.saleOrderCurrent.setSale_order_id(
                                        DefaultFirst1.saleOrder_current_id + i
                                    )


                                    usersRef.child((DefaultFirst1.saleOrder_current_id + i).toString())
                                        .setValue(DefaultFirst1.saleOrderCurrent)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                progressDialog.dismiss()
                                                if(flag == 0) {
                                                    Toast.makeText(
                                                        this,
                                                        "Đặt hàng thành công",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    flag =1
                                                }
                                                DefaultFirst1.manggiohang.clear()
                                                //openActivity(MainActivity::class.java,true)

                                            } else {
                                                val message = task.exception!!.toString()
                                                Toast.makeText(
                                                    this,
                                                    "Error: $message",
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()
                                                FirebaseAuth.getInstance().signOut()
                                                progressDialog.dismiss()
                                            }
                                        }
                                }
                                progressDialog.dismiss()
                                // het thanh toan truc tiep
                            } else {
                                //chuyen man code thanh toan oline
                                openActivity(ZaloPayActivity::class.java, true)

                            }

                        } else {
                            Toast.makeText(
                                this,
                                "Xin lỗi ,kho hàng hiện tại không đủ hàng cho đơn của bạn",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            val negativeButtonClick = { dialog: DialogInterface, which: Int ->

            }
            val builder = AlertDialog.Builder(this)

            with(builder)
            {
                setTitle("Xác nhận đặt hàng")
                setMessage("Bạn có chắc muốn đặt hàng")
                setPositiveButton(
                    "Có",
                    DialogInterface.OnClickListener(function = positiveButtonClick)
                )
                setNegativeButton("Không", negativeButtonClick)
                show()
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
        binding.toobar!!.setNavigationOnClickListener {
            openActivity(CartActivity::class.java,true)
        }
    }

}