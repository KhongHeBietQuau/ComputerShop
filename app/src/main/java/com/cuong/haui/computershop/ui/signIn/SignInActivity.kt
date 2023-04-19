package com.cuong.haui.computershop.ui.signIn

import android.app.ProgressDialog
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignInBinding
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.signUp.SignUpActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class SignInActivity : BaseActivity<ActivitySignInBinding>() {
    var database = FirebaseDatabase.getInstance()
    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySignInBinding {
        return ActivitySignInBinding.inflate(inflater)
    }


    override fun initCreate() {
        initListener()
        binding.signupLinkBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.loginBtn.setOnClickListener {
            loginUser()
        }
    }


    private fun initListener() {

    }

    private fun loginUser() {
        val email = binding.emailLogin.text.toString()
        val password = binding.passwordLogin.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this, "email is required", Toast.LENGTH_LONG)
                .show()
            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "password is required",
                Toast.LENGTH_LONG
            ).show()
            else -> {
                val progressDialog = ProgressDialog(this@SignInActivity)
                progressDialog.setTitle("Login")
                progressDialog.setMessage("Vui lòng đợi trong giây lát ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()


                mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            progressDialog.dismiss()
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

                            startActivity(intent)
                            finish()
                        } else {
                            val message = task.exception!!.toString()
                            Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
                            FirebaseAuth.getInstance().signOut()
                            progressDialog.dismiss()
                        }
                    }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            val user = Firebase.auth.currentUser
            user?.let {
                val name = it.displayName
                val email = it.email
                var myRef : DatabaseReference = database.getReference("Users").child(name.toString())
                myRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        DefaultFirst1.userCurrent = dataSnapshot.getValue(User::class.java)
                        //Toast.makeText(applicationContext, DefaultFirst1.userCurrent.toString() , Toast.LENGTH_LONG).show()
                        if(DefaultFirst1.userCurrent.role==1){
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                        else if(DefaultFirst1.userCurrent.role==2){
                            val intent = Intent(this@SignInActivity, MainAdminActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Getting Post failed, log a message
                        Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                        // ...
                    }
                })

            }

        }
    }

}