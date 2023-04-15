package com.cuong.haui.computershop.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.OptionAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityMainBinding
import com.cuong.haui.computershop.di.module.OptionSupport
import com.cuong.haui.computershop.utils.ViewUtils

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(
    MainViewModel::class.java
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        ViewUtils.setCorners(resources,25f,binding.viewlipper)
        ActionBar()
        if (isConnected(this)) {
            Toast.makeText(applicationContext, "ok", Toast.LENGTH_LONG).show()
            ActionViewFlipper()
            //themList()
            //getSpMoi()
            //getLoaiSanPham();
            //getEventClick()
        } else {
            Toast.makeText(applicationContext, "ko co internet", Toast.LENGTH_LONG).show()
        }
    }
    private fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        return if (wifi != null && wifi.isConnected || mobile != null && mobile.isConnected) {
                    true
        } else {
                    false
        }
    }

    private fun ActionViewFlipper() {
        val mangquangcao: MutableList<String> = ArrayList()
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png")
        for (i in mangquangcao.indices) {
            val imageView = ImageView(applicationContext)
            Glide.with(applicationContext).load(mangquangcao[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            binding.viewlipper.addView(imageView)
        }
        binding.viewlipper.setFlipInterval(3000)
        binding.viewlipper.setAutoStart(true)
        val slide_in = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_in_right
        )
        val slide_out = AnimationUtils.loadAnimation(
            applicationContext, R.anim.slide_out_right
        )
        binding.viewlipper.setInAnimation(slide_in)
        binding.viewlipper.setOutAnimation(slide_out)
    }
    private fun ActionBar() {
        setSupportActionBar(binding.toobarmanhinhchinh)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
       // binding.toobarmanhinhchinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
        binding.toobarmanhinhchinh.setNavigationOnClickListener(View.OnClickListener {
            binding.drawerlayout.openDrawer(
                GravityCompat.START
            )
        })
    }
    private fun initData() {
        var arrayOption : ArrayList<OptionSupport> = ArrayList()
        arrayOption.add(OptionSupport("Trang chủ",R.drawable.house))
        arrayOption.add(OptionSupport("Laptop văn phòng",R.drawable.laptop))
        arrayOption.add(OptionSupport("Laptop gaming",R.drawable.laptop_gaming))
        arrayOption.add(OptionSupport("Quản lý đơn hàng",R.drawable.clock))
        arrayOption.add(OptionSupport("Đơn nháp",R.drawable.draft))
        arrayOption.add(OptionSupport("Đặt lịch bảo hành",R.drawable.guarantee))
        arrayOption.add(OptionSupport("Thông báo",R.drawable.notification))
        arrayOption.add(OptionSupport("Chat với nhân viên",R.drawable.speak))
        arrayOption.add(OptionSupport("Gọi điện",R.drawable.phone))
        arrayOption.add(OptionSupport("Đổi mật khẩu",R.drawable.reset_password))
        arrayOption.add(OptionSupport("cài đặt",R.drawable.gear))
        binding.listviewmanhinhchinh.adapter = OptionAdapter(this@MainActivity,arrayOption)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(viewModel: MainViewModel) {}

}