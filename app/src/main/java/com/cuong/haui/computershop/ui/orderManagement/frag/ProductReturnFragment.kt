package com.cuong.haui.computershop.ui.orderManagement.frag

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.ConfirmAdapter
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentProductReturnBinding
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.database.*


class ProductReturnFragment : BaseFragment<FragmentProductReturnBinding>(){
    var database = FirebaseDatabase.getInstance()
    private lateinit var confirmspAdapter : ConfirmAdapter
    private var mangSaleOrder  = ArrayList<SaleOrder>()
    override fun initViewCreated() {
        InitData()
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
                    if(saleOrder != null  && saleOrder.status.equals("0")){
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
        binding.recyclerViewProductReturn.setLayoutManager(layoutManager)
        binding.recyclerViewProductReturn.setHasFixedSize(true)
        confirmspAdapter = ConfirmAdapter(this.activity, mangSaleOrder)
        binding.recyclerViewProductReturn.setAdapter(confirmspAdapter)
    }
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProductReturnBinding {
        return FragmentProductReturnBinding.inflate(inflater)
    }

}