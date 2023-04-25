package com.cuong.haui.computershop.ui.laptopOffice

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.LaptopAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityLaptopOfficeBinding
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.database.*

class LaptopOfficeActivity : BaseActivity<ActivityLaptopOfficeBinding>() {
    var database = FirebaseDatabase.getInstance()
    private lateinit var spAdapter : LaptopAdapter
    private var mangSpMoi  = ArrayList<SanPhamMoi>()
    override fun initCreate() {
        InitData()
        SearchChange()
        CloseScreenLaptopGaming()
    }
    private fun CloseScreenLaptopGaming() {
        binding.returnApp.setOnSafeClick {
            openActivity(MainActivity::class.java,true)
        }
    }

    private fun InitData(){
        var myRef : DatabaseReference = database.getReference("Products")
        val query = FirebaseDatabase.getInstance().getReference()
            .child("Products").orderByChild("description").equalTo("laptop văn phòng")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val sanPhamMoi = postSnapshot.getValue(SanPhamMoi::class.java)
                    if(sanPhamMoi != null){
                        mangSpMoi.add(sanPhamMoi)

                    }
                }
                spAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.recyclerViewSearch.setLayoutManager(layoutManager)
        binding.recyclerViewSearch.setHasFixedSize(true)
        spAdapter = LaptopAdapter(applicationContext, mangSpMoi)
        binding.recyclerViewSearch.setAdapter(spAdapter)
    }
    private fun SearchChange() {
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(binding.searchEditText.text.toString() == ""){
                    InitData()
                }
                else{
                    binding.recyclerViewSearch?.visibility = View.VISIBLE
                    retrieveUsers()
                    searchUser(p0.toString())
                }
            }
        })
    }

    private fun searchUser(input: String) {
        val query = FirebaseDatabase.getInstance().getReference()
            .child("Products")
            .orderByChild("product_name")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                mangSpMoi?.clear()
                for(snapshot in dataSnapshot.children){
                    val sanPhamMoi = snapshot.getValue(SanPhamMoi::class.java)
                    if(sanPhamMoi != null && sanPhamMoi.description.equals("laptop văn phòng")){
                        mangSpMoi?.add(sanPhamMoi)
                    }
                }
                spAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun retrieveUsers() {
        val usersRef = FirebaseDatabase.getInstance().getReference().child("Users")
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(binding.searchEditText?.text.toString() == "")
                {
                    mangSpMoi?.clear()
                    for(snapshot in dataSnapshot.children){
                        val sanPhamMoi = snapshot.getValue(SanPhamMoi::class.java)
                        if(sanPhamMoi != null){
                            mangSpMoi?.add(sanPhamMoi)
                        }
                    }
                    spAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityLaptopOfficeBinding {
        return ActivityLaptopOfficeBinding.inflate(inflater)
    }

}