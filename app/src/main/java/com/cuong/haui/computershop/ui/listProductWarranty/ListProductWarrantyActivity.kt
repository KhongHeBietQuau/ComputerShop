package com.cuong.haui.computershop.ui.listProductWarranty

import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.ProductWarrantyAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityListProductWarrantyBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListProductWarrantyActivity : BaseActivity<ActivityListProductWarrantyBinding>() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var confirmspAdapter : ProductWarrantyAdapter
    private var mangSaleOrder  = ArrayList<SaleOrder>()
    override fun initCreate() {

        CloseScreen()
        InitData()
        checkDateWarranty()
    }

    private fun checkDateWarranty() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        System.out.println("Current Date " + dateFormat.format(date))



        // Convert Date to Calendar
        val c: Calendar = Calendar.getInstance()
        c.setTime(date)

        c.add(Calendar.MONTH, 1)

        val currentDatePlusOne: Date = c.getTime()
        Toast.makeText(applicationContext, "Updated Date " + dateFormat.format(currentDatePlusOne), Toast.LENGTH_LONG).show()
    }

    private fun InitData(){
        //Toast.makeText(activity, "ngu", Toast.LENGTH_LONG).show()
        var myRef : DatabaseReference = database.getReference("SaleOrders")
        val query = FirebaseDatabase.getInstance().getReference()
            .child("SaleOrders").orderByChild("user_id").equalTo(DefaultFirst1.userCurrent.user_id.toDouble())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangSaleOrder.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val saleOrder = postSnapshot.getValue(SaleOrder::class.java)
                    if(saleOrder != null && saleOrder.status.equals("3")){
                        //Toast.makeText(activity, "okkk", Toast.LENGTH_LONG).show()
                        mangSaleOrder.add(saleOrder)

                    }
                }
                confirmspAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
//
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.recyclerListProductWarranty.setLayoutManager(layoutManager)
        binding.recyclerListProductWarranty.setHasFixedSize(true)
        confirmspAdapter = ProductWarrantyAdapter(this, mangSaleOrder)
        binding.recyclerListProductWarranty.setAdapter(confirmspAdapter)
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityListProductWarrantyBinding {
        return ActivityListProductWarrantyBinding.inflate(inflater)
    }
    private fun CloseScreen() {
        binding.returnApp.setOnSafeClick {
            openActivity(MainActivity::class.java,true)
        }
    }
}