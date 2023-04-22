package com.cuong.haui.computershop.ui.chatAdmin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.cuong.haui.computershop.adpter.MessageAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityChatAdminBinding
import com.cuong.haui.computershop.databinding.ActivityChatBinding
import com.cuong.haui.computershop.model.MessageChat
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.database.*

class ChatAdminActivity : BaseActivity<ActivityChatAdminBinding>() {
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<MessageChat>
    private lateinit var mDbRef: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun initCreate() {
        CloseScreen()
        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid").toString()
        val senderUid = DefaultFirst1.userCurrent.user_id.toString()
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        binding.txtNameUserFriend.setText(name.toString())


        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)

        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        mDbRef.child("Chats").child(senderRoom!!).child("messages").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(MessageChat::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.sentButton.setOnClickListener{
            val message = binding.messageBox.text.toString()
            val messageObject = MessageChat(message,senderUid)
            mDbRef.child("Chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("Chats").child(receiverRoom!!).child("messages").push().setValue(messageObject)
            }
            binding.messageBox.setText("")
        }
    }
    private fun CloseScreen() {

            binding.returnApp.setOnSafeClick {
                finish()
            }

    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityChatAdminBinding {
        return ActivityChatAdminBinding.inflate(inflater)
    }

}