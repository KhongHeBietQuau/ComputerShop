package com.cuong.haui.computershop.ui.signUp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignInBinding
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : BaseActivity<ActivitySignUpBinding>() {
    override fun initCreate() {
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
    private fun CreateAccount() {
        val fullName = binding.fullnameSignup.text.toString()
        val userName = binding.usernameSignup.text.toString()
        val email = binding.emailSignup.text.toString()
        val password = binding.passwordSignup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"full name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this,"user name is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"email is required", Toast.LENGTH_LONG).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"password is required", Toast.LENGTH_LONG).show()


            else ->{
                val progressDialog = ProgressDialog(this@SignUpActivity)
                progressDialog.setTitle("SignUp")
                progressDialog.setMessage("Please wait, this may take a while ...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener{ task ->
                        if(task.isSuccessful){
                            saveUserInfo(fullName,userName,email,progressDialog)
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

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullName.toLowerCase()
        userMap["username"] = userName.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "hey i am using Coding Cafe Instagram Clone App."
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/instagram-clone-deeb5.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=644c083d-fa83-490b-af51-a8855fcdd94b"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this,"Account has been created successfull", Toast.LENGTH_LONG).show()

                    val intent = Intent(this@SignUpActivity,MainActivity::class.java)
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