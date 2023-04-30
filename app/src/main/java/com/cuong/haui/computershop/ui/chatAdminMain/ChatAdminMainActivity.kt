package com.cuong.haui.computershop.ui.chatAdminMain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuong.haui.computershop.adpter.UserAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityChatAdminMainBinding
import com.cuong.haui.computershop.databinding.ActivityChatMainBinding
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatAdminMainActivity : BaseActivity<ActivityChatAdminMainBinding>() {
    private lateinit var userList:ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun initCreate() {
        CloseScreen()
        mAuth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this,userList)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        mDbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for(postSnashot in snapshot.children){
                    val currentUser = postSnashot.getValue(User::class.java)
                    if(DefaultFirst1.userCurrent.user_id != currentUser?.user_id && currentUser?.role == 1)
                        userList.add(currentUser!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun CloseScreen() {
        binding.returnApp.setOnSafeClick {
            finish()
        }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityChatAdminMainBinding {
        return ActivityChatAdminMainBinding.inflate(inflater)
    }

}