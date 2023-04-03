package com.cuong.haui.computershop.base

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.utils.Utils
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>) :
    DaggerAppCompatActivity() {


    @Inject
    internal lateinit var viewModelProviderFactory: ViewModelProvider.Factory

    @LayoutRes
    abstract fun getLayoutRes(): Int

    val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    val viewModel by lazy {
        ViewModelProvider(this, viewModelProviderFactory).get(mViewModelClass)
    }

    open fun onInject() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        initViewModel(viewModel)
        onInject()
        setupBindingLifecycleOwner()
    }

    abstract fun initViewModel(viewModel: VM)

    private fun setupBindingLifecycleOwner() {
        binding.lifecycleOwner = this
    }

    fun getPermission(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }


    fun checkPermission(per: Array<String>): Boolean {
        for (s in per) {
            if (checkSelfPermission(s) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private val readAndWritePermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    open fun isReadAndWritePermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            return Environment.isExternalStorageManager()
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            for (permission in readAndWritePermissions) {
                if (checkSelfPermission(permission) !== PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    open fun checkPermission() {
        val arrPermission: java.util.ArrayList<String?> = ArrayList<String?>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            checkAndRequestExternalPermissionAndroid11()
        } else if (Build.VERSION.SDK_INT >= 23) {
            if (!Utils.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                arrPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (!Utils.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                arrPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (arrPermission.isNotEmpty()) {
                requestPermissions(
                    arrPermission.toTypedArray(),
                    100
                )
            } else {
                // scan
            }
        } else {
            //can
        }
    }

    private val requestExternalPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !Environment.isExternalStorageManager()) {
            Toast.makeText(
                this,  R.string.denied_permission, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(
                this, R.string.granted_permission, Toast.LENGTH_LONG).show()
        }
    }

    private fun checkAndRequestExternalPermissionAndroid11() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q && !Environment.isExternalStorageManager()) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.permission_title)
            builder.setMessage(R.string.request_permission_cause)
            builder.setPositiveButton(getString(R.string.ok)) { dialogInterface: DialogInterface?, i: Int ->
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                requestExternalPermissionLauncher.launch(intent)
            }
            builder.setNegativeButton(R.string.cancel_dialog) { dialogInterface, i ->
                Toast.makeText(
                    this,
                    R.string.denied_permission,
                    Toast.LENGTH_LONG
                ).show()
            }
            builder.create().show()
        }
    }


    fun setBackgroundStatusBar(){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    }

}