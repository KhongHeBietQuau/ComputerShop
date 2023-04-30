package com.cuong.haui.computershop.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivitySignUpBinding
import com.cuong.haui.computershop.databinding.ActivitySplashBinding
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.main.MainHostActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    var database = FirebaseDatabase.getInstance()
    override fun initCreate() {

        Handler(Looper.getMainLooper()).postDelayed({
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
                                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                            else if(DefaultFirst1.userCurrent.role==2){
                                val intent = Intent(this@SplashActivity, MainAdminActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                            else if(DefaultFirst1.userCurrent.role==3){
                                val intent = Intent(this@SplashActivity, MainHostActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            }
                            else if(DefaultFirst1.userCurrent.role<0){
                                Toast.makeText(applicationContext, "Tài khoản của bạn bị khóa" , Toast.LENGTH_LONG).show()
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
            else{
                openActivity(SignInActivity::class.java,true)
            }
        }, 2000L)
    }

    private fun startPlay() {
        openActivity(SignInActivity::class.java,true)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(inflater)
    }
    override fun onStart() {
        super.onStart()

    }
}