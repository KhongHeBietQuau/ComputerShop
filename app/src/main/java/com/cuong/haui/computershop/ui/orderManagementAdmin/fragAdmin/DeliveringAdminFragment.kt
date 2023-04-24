package com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.ComfirmAdminAdapter
import com.cuong.haui.computershop.adpter.ConfirmAdapter
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentDeliveringAdminBinding
import com.cuong.haui.computershop.databinding.FragmentDeliveringBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.database.*


class DeliveringAdminFragment : BaseFragment<FragmentDeliveringAdminBinding>() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var confirmspAdapter : ComfirmAdminAdapter
    private var mangSaleOrder  = ArrayList<SaleOrder>()
    override fun initViewCreated() {
        InitData()
    }
    private fun InitData(){
        //Toast.makeText(activity, "ngu", Toast.LENGTH_LONG).show()
        var myRef : DatabaseReference = database.getReference("SaleOrders")
        val query = FirebaseDatabase.getInstance().getReference()
            .child("SaleOrders").orderByChild("user_id").equalTo(DefaultFirst1.userCurrent.user_id.toDouble())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangSaleOrder.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val saleOrder = postSnapshot.getValue(SaleOrder::class.java)
                    if(saleOrder != null && saleOrder.status.equals("2")){
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
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this.getActivity(), 1)
        binding.recyclerViewDelivering.setLayoutManager(layoutManager)
        binding.recyclerViewDelivering.setHasFixedSize(true)
        confirmspAdapter = ComfirmAdminAdapter(this.activity, mangSaleOrder)
        binding.recyclerViewDelivering.setAdapter(confirmspAdapter)
    }
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveringAdminBinding {
        return FragmentDeliveringAdminBinding.inflate(inflater)
    }

}