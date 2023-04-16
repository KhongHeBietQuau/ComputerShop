package com.cuong.haui.computershop.ui.addProduct

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageManager {
    private val TAG = "FirebaseStorageManager"
    private val mStorageRef = FirebaseStorage.getInstance().reference
    private lateinit var mProgressDialog: ProgressDialog
    fun uploadImage(mContext: Context,imageURI : Uri){
        mProgressDialog = ProgressDialog(mContext)
        mProgressDialog.setMessage("Please wait,image being uploading...")
        mProgressDialog.show()
        val imageFileName = "users/profilePic${System.currentTimeMillis()}.png"
        val uploadTask =mStorageRef.child(imageFileName).putFile(imageURI)
        uploadTask.addOnSuccessListener {
            Log.e(TAG,"Image upload successfully")
            val downloadURLTask = mStorageRef.child("users/profilePic${System.currentTimeMillis()}.png").downloadUrl
            downloadURLTask.addOnSuccessListener {
                Log.e(TAG,"IMAGE PATH : $it")
                mProgressDialog.dismiss()
            }.addOnFailureListener {
                mProgressDialog.dismiss()
            }.addOnFailureListener {
                Log.e(TAG,"Failed")
            }
        }

    }

}