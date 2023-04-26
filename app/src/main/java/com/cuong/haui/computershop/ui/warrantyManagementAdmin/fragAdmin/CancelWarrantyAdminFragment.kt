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
import com.cuong.haui.computershop.base.BaseFragment
import com.cuong.haui.computershop.databinding.FragmentCancelWarrantyAdminBinding
import com.cuong.haui.computershop.databinding.FragmentSuccessfulWarrantyBinding
import com.cuong.haui.computershop.model.Maintenance
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.database.*


class CancelWarrantyAdminFragment : BaseFragment<FragmentCancelWarrantyAdminBinding>() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var confirmspAdapter : ComfirmWarrantyAdapter
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
                    if(maintenance != null && maintenance.status_maintenance.equals("0")){
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
        binding.recyclerViewProductReturn.setLayoutManager(layoutManager)
        binding.recyclerViewProductReturn.setHasFixedSize(true)
        confirmspAdapter = ComfirmWarrantyAdapter(this.activity, mangMaintenance)
        binding.recyclerViewProductReturn.setAdapter(confirmspAdapter)
    }
    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCancelWarrantyAdminBinding {
        return FragmentCancelWarrantyAdminBinding.inflate(inflater)
    }

}