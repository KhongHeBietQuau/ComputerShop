package com.cuong.haui.computershop.ui.bookWarranty

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityBookWarrantyBinding
import com.cuong.haui.computershop.model.Maintenance
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.model.Store
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BookWarrantyActivity : BaseActivity<ActivityBookWarrantyBinding>() {
    private var mangStore  = ArrayList<Store>()
    private lateinit var database : DatabaseReference
    var maintenance_id : Int = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun initCreate() {
        getElementLast()
        CloseScreen()
        PickDate()
        PickStore()
        BookCalendar()
    }
    private fun getElementLast(){
        database = FirebaseDatabase.getInstance().getReference("BookWarrantys")
        var databaseIndexLast: Query
        databaseIndexLast = database.limitToLast(1)
        var mangMaintenance = ArrayList<Maintenance>()
        databaseIndexLast.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val maintenance = postSnapshot.getValue(Maintenance::class.java)
                    if (maintenance != null) {
                        mangMaintenance.add(maintenance)
                        maintenance_id = maintenance.maintenance_id+1

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
    private fun BookCalendar() {
        binding.btnSubmitMaintain.setOnSafeClick {
            val problemMaintain = binding.edtProblemMaintain.text.toString()
            val pickDateWarranty = binding.edtPickDateWarranty.text.toString()
            val addressStore = binding.spinnerr!!.selectedItem.toString()
            Toast.makeText(applicationContext, addressStore.toString(), Toast.LENGTH_LONG).show()

            when {
                TextUtils.isEmpty(problemMaintain) -> Toast.makeText(
                    this,
                    "Vui lòng nhập vấn để của máy",
                    Toast.LENGTH_LONG
                ).show()
                TextUtils.isEmpty(pickDateWarranty) -> Toast.makeText(
                    this,
                    "Vui lòng chọn ngày đặt lịch",
                    Toast.LENGTH_LONG
                ).show()
                TextUtils.isEmpty(addressStore) -> Toast.makeText(this,"Vui lòng chọn cửa hàng bạn muốn đặt lịch ", Toast.LENGTH_LONG).show()


                else -> {
                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Đăng kí bảo dưỡng")
                    progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                    progressDialog.setCanceledOnTouchOutside(false)
                    progressDialog.show()
                    var saleOrderWarranty = intent.getSerializableExtra("saleOrderWarranty") as SaleOrder?
                    val usersRef: DatabaseReference =
                        FirebaseDatabase.getInstance().reference.child("BookWarrantys")
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    val current = LocalDateTime.now().format(formatter)
                    var maintenance = saleOrderWarranty?.let { it1 ->
                        Maintenance(maintenance_id,DefaultFirst1.userCurrent.user_id,0,saleOrderWarranty.sale_order_id,
                            it1.idsp,current,current,problemMaintain,saleOrderWarranty?.hinhsp,addressStore,pickDateWarranty,"1")
                    }

                    usersRef.child(maintenance_id.toString())
                        .setValue(maintenance)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                progressDialog.dismiss()
                                Toast.makeText(
                                    this,
                                    "Đặt lịch bảo hành thành công",
                                    Toast.LENGTH_LONG
                                ).show()
                                DefaultFirst1.manggiohang.clear()
                                //openActivity(MainActivity::class.java,true)

                            } else {
                                val message = task.exception!!.toString()
                                Toast.makeText(this, "Lỗi", Toast.LENGTH_LONG)
                                    .show()
                                FirebaseAuth.getInstance().signOut()
                                progressDialog.dismiss()
                            }
                        }

                }
            }
        }
    }

    private fun PickStore() {

        //
        var myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("StoreLists")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val store = postSnapshot.getValue(Store::class.java)
                    if(store != null){
                        mangStore.add(store)
                        //Toast.makeText(applicationContext, "haha", Toast.LENGTH_LONG).show()
                    }
                }
                val so = ArrayList<String>()
                for (i in 0 until mangStore.size) {
                    so.add(mangStore.get(i).address.toString())
                }

                val adapterspin = ArrayAdapter(applicationContext, R.layout.support_simple_spinner_dropdown_item, so)
                binding.spinnerr!!.adapter = adapterspin
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

    }

    private fun PickDate() {
        binding.edtPickDateWarranty.setOnSafeClick {

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(

                this,
                { view, year, monthOfYear, dayOfMonth ->

                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    binding.edtPickDateWarranty.setText(dat)
                },

                year,
                month,
                day
            )
            datePickerDialog.show()
        }
    }
    private fun CloseScreen() {
        binding.returnApp.setOnSafeClick {
            finish()
        }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityBookWarrantyBinding {
        return ActivityBookWarrantyBinding.inflate(inflater)
    }

}