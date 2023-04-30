package com.cuong.haui.computershop.ui.userManagement

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cuong.haui.computershop.adpter.StoreAdapter
import com.cuong.haui.computershop.adpter.UserManagementAdpter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityUserManagementBinding
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.model.Store
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.main.MainHostActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.database.*

class UserManagementActivity : BaseActivity<ActivityUserManagementBinding>() {
    private lateinit var database : DatabaseReference
    private lateinit var userAdapter: UserManagementAdpter
    private var mangUser  = ArrayList<User>()
    var userRole1 = 0
    var userRole2 = 0
    override fun initCreate() {
        if(DefaultFirst1.userCurrent.role == 2){
            userRole1 = 1
            userRole2 = -1
        }
         if(DefaultFirst1.userCurrent.role == 3){
            userRole1 = 2
            userRole2 = -2
        }
        if(DefaultFirst1.userCurrent.role ==3){
            binding.txtNameUserFriend.setText("Quản lý nhân viên")
        }
        CloseScreen()
        InitData()
        SearchChange()
    }
    private fun CloseScreen() {
        binding.returnApp.setOnSafeClick {
           finish()
        }
    }
    private fun InitData() {

        var myRef : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangUser.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)
                    if(user != null && (user.role == userRole1 || user.role ==userRole2)){
                        mangUser.add(user)

                    }
                }
                userAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.userListRecyclerView.setLayoutManager(layoutManager)
        binding.userListRecyclerView.setHasFixedSize(true)
        userAdapter = UserManagementAdpter(applicationContext, mangUser)
        binding.userListRecyclerView.setAdapter(userAdapter)
        //mangStore.clear()
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
                    binding.userListRecyclerView?.visibility = View.VISIBLE
                    retrieveUsers()
                    searchUser(p0.toString())
                }
            }
        })
    }

    private fun searchUser(input: String) {
        val query = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .orderByChild("email")
            .startAt(input)
            .endAt(input + "\uf8ff")

        query.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot)
            {
                mangUser?.clear()
                for(snapshot in dataSnapshot.children){
                    val user = snapshot.getValue(User::class.java)
                    if(user != null  && (user.role == userRole1 || user.role == userRole2)){
                        mangUser?.add(user)
                    }
                }
                userAdapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun retrieveUsers() {
        val usersRef = FirebaseDatabase.getInstance().getReference().child("Users")
        usersRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(binding.searchEditText?.text.toString() == "")
                {
                    mangUser?.clear()
                    for(snapshot in dataSnapshot.children){
                        val user = snapshot.getValue(User::class.java)
                        if(user != null && (user.role == userRole1 || user.role == userRole2)){
                            mangUser?.add(user)
                        }
                    }
                    userAdapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityUserManagementBinding {
        return ActivityUserManagementBinding.inflate(inflater)
    }

}