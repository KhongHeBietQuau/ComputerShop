package com.cuong.haui.computershop.ui.orderManagementAdmin.fragAdmin

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.ComfirmAdminAdapter
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentConfirmAdminBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.database.*


class ConfirmAdminFragment : BaseFragment<FragmentConfirmAdminBinding>() {
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
                    if(saleOrder != null && saleOrder.status.equals("1")){
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
        binding.recyclerViewConfirm.setLayoutManager(layoutManager)
        binding.recyclerViewConfirm.setHasFixedSize(true)
        confirmspAdapter = ComfirmAdminAdapter(this.activity, mangSaleOrder)
        binding.recyclerViewConfirm.setAdapter(confirmspAdapter)
    }
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmAdminBinding {
        return FragmentConfirmAdminBinding.inflate(inflater)
    }

}