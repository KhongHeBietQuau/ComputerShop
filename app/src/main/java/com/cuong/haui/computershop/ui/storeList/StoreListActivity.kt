package com.cuong.haui.computershop.ui.storeList

import android.app.ProgressDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.SanPhamMoiAdapter
import com.cuong.haui.computershop.adpter.StoreAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityStoreListBinding
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.model.Store
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class StoreListActivity : BaseActivity<ActivityStoreListBinding>() {
    private lateinit var database : DatabaseReference
    private lateinit var storeAdapter: StoreAdapter
    private var mangStore  = ArrayList<Store>()
    var store_id : Int = 0
    override fun initCreate() {
        getElementLast()
        addStore()
        ReadDataStore()
    }

    private fun ReadDataStore() {

        var myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("StoreLists")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangStore.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val store = postSnapshot.getValue(Store::class.java)
                    if(store != null){
                        mangStore.add(store)

                    }
                }
                storeAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.storeListRecyclerView.setLayoutManager(layoutManager)
        binding.storeListRecyclerView.setHasFixedSize(true)
        storeAdapter = StoreAdapter(applicationContext, mangStore)
        binding.storeListRecyclerView.setAdapter(storeAdapter)
        //mangStore.clear()
    }

    private fun getElementLast() {
        database = FirebaseDatabase.getInstance().getReference("StoreLists")
        var databaseIndexLast: Query
        databaseIndexLast = database.limitToLast(1)
        var mangStore = ArrayList<Store>()
        databaseIndexLast.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val store = postSnapshot.getValue(Store::class.java)
                    if (store != null) {
                        mangStore.add(store)
                        store_id = store.store_id+1
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

    private fun addStore() {
        binding.btnAddStore.setOnSafeClick {
            if(binding.edtAddStore.text != null){
                val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("StoreLists")
                val store = Store(store_id,binding.edtAddStore.text.toString())
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Thêm cửa hàng")
                progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()
                usersRef.child(store_id.toString()).setValue(store)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful){
                            progressDialog.dismiss()
                            Toast.makeText(this,"Thêm thành công", Toast.LENGTH_LONG).show()

                        }
                        else{
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
            else{

            }
        }

    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityStoreListBinding {
        return ActivityStoreListBinding.inflate(inflater)
    }

}