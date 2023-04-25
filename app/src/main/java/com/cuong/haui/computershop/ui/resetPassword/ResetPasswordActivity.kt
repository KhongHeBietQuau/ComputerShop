package com.cuong.haui.computershop.ui.resetPassword

import android.view.LayoutInflater
import android.widget.Toast
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityResetPasswordBinding
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ResetPasswordActivity : BaseActivity<ActivityResetPasswordBinding>() {
    override fun initCreate() {
        CloseScreen()

        binding.btnResetPassword.setOnSafeClick {
        if(DefaultFirst1.userCurrent.password.toString().equals(binding.passwordOld.getText().toString())){

            if(binding.passwordNew.text.toString().equals(binding.passwordNewRepeat.text.toString())){
                //Toast.makeText(applicationContext, binding.passwordNew.text.toString().equals(binding.passwordNewRepeat.text.toString()).toString(), Toast.LENGTH_LONG).show()
                    val user = Firebase.auth.currentUser
                    val newPassword = binding.passwordNew.getText().toString()

                    user!!.updatePassword(newPassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val database = FirebaseDatabase.getInstance()
                                val mDbRef = database.getReference("Users/"+DefaultFirst1.userCurrent.user_id.toString()+"/password")
                                mDbRef.setValue(newPassword)
                                Toast.makeText(applicationContext, "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show()
                            }
                        }.addOnCanceledListener {
                            Toast.makeText(applicationContext, "Lỗi , vui lòng xem lại thông tin vừa nhập", Toast.LENGTH_LONG).show()
                        }
                }
            }
            else{
            Toast.makeText(applicationContext, "Lỗi , vui lòng xem lại thông tin vừa nhập", Toast.LENGTH_LONG).show()
        }
        }

    }
    private fun CloseScreen() {
        binding.returnApp.setOnSafeClick {
            openActivity(MainActivity::class.java,true)
        }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityResetPasswordBinding {
        return ActivityResetPasswordBinding.inflate(inflater)
    }

}