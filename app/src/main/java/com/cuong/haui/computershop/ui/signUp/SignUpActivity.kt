package com.cuong.haui.computershop.ui.signUp

import android.app.ProgressDialog
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*


class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    private lateinit var database : DatabaseReference
    var user_id : Int = 0
    override fun initCreate() {
        getElementLast()
        binding.signinLinkBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        binding.signupBtn.setOnClickListener {
            CreateAccount()
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySignUpBinding {
        return ActivitySignUpBinding.inflate(inflater)
    }
    private fun getElementLast(){
        database = FirebaseDatabase.getInstance().getReference("Users")
        var databaseIndexLast: Query
        databaseIndexLast = database.limitToLast(1)
        var mangUser = ArrayList<User>()
        databaseIndexLast.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)
                    if (user != null) {
                        mangUser.add(user)
                        user_id = user.user_id+1
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
    private fun CreateAccount() {
        val fullName = binding.fullnameSignup.text.toString()
        val userName = binding.usernameSignup.text.toString()
        val email = binding.emailSignup.text.toString()
        val password = binding.passwordSignup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"Không được bỏ trống họ tên", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"Không được bỏ trống tên tài khoản", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Không được bỏ trống email ", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Không được bỏ trống mật khẩu", Toast.LENGTH_LONG).show()


            else ->{
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("Đăng kí")
                progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            saveUserInfo(fullName,userName,email,progressDialog,password)
                            // truyền thêm dữ liệu cho tài khoản Authenication hiện tại
                            val user = FirebaseAuth.getInstance().currentUser

                            val profileUpdates =
                                UserProfileChangeRequest.Builder().setDisplayName(user_id.toString())
                                    .build()

                            user!!.updateProfile(profileUpdates)
                        }
                        else{
                            val message = task.exception!!.toString()
                            Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                            mAuth.signOut()
                            progressDialog.dismiss()

                        }
                    }
            }
        }
    }

    private fun saveUserInfo(fullName: String?, userName: String?, email: String?, progressDialog: ProgressDialog, password: String?) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = User(user_id,1,fullName,userName,email,password,"","","https://firebasestorage.googleapis.com/v0/b/instagram-clone-deeb5.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=644c083d-fa83-490b-af51-a8855fcdd94b")
        DefaultFirst1.userCurrent.setUser_id(user_id)
        DefaultFirst1.userCurrent.setRole(1)
        DefaultFirst1.userCurrent.setFullname(fullName)
        DefaultFirst1.userCurrent.setUsername(userName)
        DefaultFirst1.userCurrent.setEmail(email)
        DefaultFirst1.userCurrent.setPassword(password)
        DefaultFirst1.userCurrent.setPhone("")
        DefaultFirst1.userCurrent.setAddress("")
        DefaultFirst1.userCurrent.setImage("https://firebasestorage.googleapis.com/v0/b/instagram-clone-deeb5.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=644c083d-fa83-490b-af51-a8855fcdd94b")

        usersRef.child(user_id.toString()).setValue(userMap)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this,"Tài khoản đã được tạo thành công", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@SignUpActivity,MainActivity::class.java)
                    intent.putExtra("userCurrent", userMap)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }
                else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }

    }
}