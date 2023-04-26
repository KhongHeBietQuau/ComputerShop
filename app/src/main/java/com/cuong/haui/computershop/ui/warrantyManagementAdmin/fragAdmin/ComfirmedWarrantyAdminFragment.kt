package com.cuong.haui.computershop.ui.warrantyManagementAdmin.fragAdmin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.ComfirmWarrantyAdapter
import com.cuong.haui.computershop.adpter.ComfirmWarrantyAdminAdapter
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentComfirmedWarrantyAdminBinding
import com.cuong.haui.computershop.databinding.FragmentSuccessfulWarrantyBinding
import com.cuong.haui.computershop.model.Maintenance
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.database.*


class ComfirmedWarrantyAdminFragment : BaseFragment<FragmentComfirmedWarrantyAdminBinding>() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var confirmspAdapter : ComfirmWarrantyAdminAdapter
    private var mangMaintenance  = ArrayList<Maintenance>()
    override fun initViewCreated() {
        InitData()
    }
    private fun InitData(){
        //Toast.makeText(activity, "ngu", Toast.LENGTH_LONG).show()
        var myRef : DatabaseReference = database.getReference("BookWarrantys")
        val query = FirebaseDatabase.getInstance().getReference()
            .child("BookWarrantys").orderByChild("user_id_maintenance").equalTo(DefaultFirst1.userCurrent.user_id.toDouble())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangMaintenance.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val maintenance = postSnapshot.getValue(Maintenance::class.java)
                    if(maintenance != null && maintenance.status_maintenance.equals("2")){
                        //Toast.makeText(activity, "okkk", Toast.LENGTH_LONG).show()
                        mangMaintenance.add(maintenance)

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
        confirmspAdapter = ComfirmWarrantyAdminAdapter(this.activity, mangMaintenance)
        binding.recyclerViewDelivering.setAdapter(confirmspAdapter)
    }
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentComfirmedWarrantyAdminBinding {
        return FragmentComfirmedWarrantyAdminBinding.inflate(inflater)
    }

}