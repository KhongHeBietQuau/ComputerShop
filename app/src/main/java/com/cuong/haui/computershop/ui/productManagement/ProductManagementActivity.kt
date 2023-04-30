package com.cuong.haui.computershop.ui.productManagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityMainAdminBinding
import com.cuong.haui.computershop.databinding.ActivityProductManagementBinding
import com.cuong.haui.computershop.ui.addProduct.addProductActivity
import com.cuong.haui.computershop.ui.main.MainActivity
import com.cuong.haui.computershop.ui.main.MainAdminActivity
import com.cuong.haui.computershop.ui.main.MainHostActivity
import com.cuong.haui.computershop.ui.stopSelling.StopSellingActivity
import com.cuong.haui.computershop.ui.updateProduct.UpdateProductActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick

class ProductManagementActivity : BaseActivity<ActivityProductManagementBinding>() {
    override fun initCreate() {
        binding.btnAddProduct.setOnSafeClick {
            openActivity(addProductActivity::class.java,false)
        }
        binding.btnUpdateProduct.setOnSafeClick {
            openActivity(UpdateProductActivity::class.java,false)
        }
        binding.btnDeleteProduct.setOnSafeClick {
            openActivity(StopSellingActivity::class.java,false)
        }
        CloseScreenLaptopGaming()
    }
    private fun CloseScreenLaptopGaming() {
        binding.returnApp.setOnSafeClick {
            finish()
        }
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityProductManagementBinding {
        return ActivityProductManagementBinding.inflate(inflater)
    }

}