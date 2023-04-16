package com.cuong.haui.computershop.ui.main

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.adpter.OptionAdapter
import com.cuong.haui.computershop.adpter.SanPhamMoiAdapter
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityMainAdminBinding
import com.cuong.haui.computershop.model.OptionSupport
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.utils.ViewUtils
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainAdminActivity : BaseActivity<ActivityMainAdminBinding>() {
    private lateinit var spAdapter : SanPhamMoiAdapter
    private var mangSpMoi  = ArrayList<SanPhamMoi>()
    override fun initCreate() {
        initData()
        inClick()
        ViewUtils.setCorners(resources,25f,binding.viewlipper)
        ActionBar()
        if (isConnected(this)) {
            Toast.makeText(applicationContext, "ok", Toast.LENGTH_LONG).show()
            ActionViewFlipper()
            //themList()
            getSpMoi()
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
        arrayOption.add(OptionSupport("Chat với khách hàng",R.drawable.speak))
        arrayOption.add(OptionSupport("Thêm hàng",R.drawable.add_compuetr))
        arrayOption.add(OptionSupport("Đổi mật khẩu",R.drawable.reset_password))
        arrayOption.add(OptionSupport("cài đặt",R.drawable.gear))
        binding.listviewmanhinhchinh.adapter = OptionAdapter(this@MainAdminActivity,arrayOption)
        // them sp moi
        // them sp moi
        mangSpMoi.add(
            SanPhamMoi(
                1,
                "Laptop Dell Vostro V3568",
                "http://mauweb.monamedia.net/hanoicomputer/wp-content/uploads/2017/12/dell-V3568-XF6C61-01.jpg",
                "12200000",
                "Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion",
                2
            )
        )

        mangSpMoi.add(
            SanPhamMoi(
                2,
                "Laptop Dell Vostro V3568",
                "https://firebasestorage.googleapis.com/v0/b/computershop-9543c.appspot.com/o/6637_ac_m___i_100__full_box_laptop_asus_rog_strix_g15_g513ih_hn015w_amd.jpg?alt=media&token=a50102d4-e401-4bf3-ad3a-9b0c2e8ccb6a",
                "12200000",
                ("Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion"),
                2
            )
        )
        mangSpMoi.add(
            SanPhamMoi(
                3,
                "Laptop Dell Vostro V3568",
                "http://mauweb.monamedia.net/hanoicomputer/wp-content/uploads/2017/12/mac-pro-2017-01.jpg",
                "12200000",
                ("Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion"),
                2
            )
        )
        mangSpMoi.add(
            SanPhamMoi(
                4,
                "Laptop Dell Vostro V3568",
                "http://mauweb.monamedia.net/hanoicomputer/wp-content/uploads/2017/12/mac-pro-2017-01.jpg",
                "12200000",
                ("Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion"),
                2
            )
        )
        mangSpMoi.add(
            SanPhamMoi(
                5,
                "Laptop Dell Vostro V3568",
                "http://mauweb.monamedia.net/hanoicomputer/wp-content/uploads/2017/12/mac-pro-2017-01.jpg",
                "12200000",
                ("Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion"),
                2
            )
        )
        mangSpMoi.add(
            SanPhamMoi(
                6,
                "Laptop Dell Vostro V3568",
                "http://mauweb.monamedia.net/hanoicomputer/wp-content/uploads/2017/12/mac-pro-2017-01.jpg",
                "12200000",
                ("Chip: Intel Core i5-7200U\n" +
                        "RAM: DDR4 4GB (2 khe cắm)\n" +
                        "Ổ cứng: HDD 1TB\n" +
                        "Chipset đồ họa: Intel HD Graphics 620\n" +
                        "Màn hình: 15.6 Inches\n" +
                        "Hệ điều hành: Free Dos\n" +
                        "Pin: 4 Cell Lithium-ion"),
                2
            )
        )
    }
    private fun inClick(){
        binding.btnDangXuat.setOnSafeClick {
            Firebase.auth.signOut()
            openActivity(SignInActivity::class.java, true)
        }
    }
    private fun getSpMoi() {
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        binding.recycleview.setLayoutManager(layoutManager)
        binding.recycleview.setHasFixedSize(true)
        spAdapter = SanPhamMoiAdapter(applicationContext, mangSpMoi)
        binding.recycleview.setAdapter(spAdapter)
    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainAdminBinding {
        return ActivityMainAdminBinding.inflate(inflater)
    }

}