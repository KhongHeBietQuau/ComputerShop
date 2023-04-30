package com.cuong.haui.computershop.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView
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
import com.cuong.haui.computershop.model.SaleOrder
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.addProduct.addProductActivity
import com.cuong.haui.computershop.ui.chatAdminMain.ChatAdminMainActivity
import com.cuong.haui.computershop.ui.laptopGaming.LaptopGamingActivity
import com.cuong.haui.computershop.ui.laptopOffice.LaptopOfficeActivity
import com.cuong.haui.computershop.ui.notificationAdmin.NotificationAdminMainActivity
import com.cuong.haui.computershop.ui.orderManagement.OrderManagementActivity
import com.cuong.haui.computershop.ui.orderManagementAdmin.OrderManagementAdminActivity
import com.cuong.haui.computershop.ui.productManagement.ProductManagementActivity
import com.cuong.haui.computershop.ui.resetPassword.ResetPasswordActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.ui.statistical.StatisticalActivity
import com.cuong.haui.computershop.ui.userManagement.UserManagementActivity
import com.cuong.haui.computershop.ui.warrantyManagementAdmin.WarrantyManagementAdminActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.utils.ViewUtils
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainAdminActivity : BaseActivity<ActivityMainAdminBinding>() {
    var database = FirebaseDatabase.getInstance()
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
            getEventClick()
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

    private fun getEventClick() {
        binding.listviewmanhinhchinh.setOnItemClickListener(AdapterView.OnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    val trangchu = Intent(applicationContext, MainAdminActivity::class.java)

                }
                1 -> {
                    val dienthoai = Intent(applicationContext, LaptopOfficeActivity::class.java)

                    startActivity(dienthoai)
                }
                2 -> {
                    val laptop = Intent(applicationContext, LaptopGamingActivity::class.java)

                    startActivity(laptop)
                }
                3 -> {
                    val laptop = Intent(applicationContext, OrderManagementAdminActivity::class.java)

                    startActivity(laptop)
                }
                4 -> {
                    val laptop = Intent(applicationContext, WarrantyManagementAdminActivity::class.java)

                    startActivity(laptop)
                }
                5 -> {
                    val laptop = Intent(applicationContext, UserManagementActivity::class.java)

                    startActivity(laptop)
                }
                6 -> {
                    val laptop = Intent(applicationContext, ProductManagementActivity::class.java)

                    startActivity(laptop)
                }

                7 -> {
                    val laptop = Intent(applicationContext, ChatAdminMainActivity::class.java)

                    startActivity(laptop)
                }
                8 -> {
                    val laptop = Intent(applicationContext, ResetPasswordActivity::class.java)

                    startActivity(laptop)
                }
                9 -> {
                    val laptop = Intent(applicationContext, StatisticalActivity::class.java)

                    startActivity(laptop)
                }
                10 -> {
                    val laptop = Intent(applicationContext, NotificationAdminMainActivity::class.java)
                    startActivity(laptop)
                }
            }
        })
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
        arrayOption.add(OptionSupport("Laptop cấu hình cao",R.drawable.laptop_gaming))
        arrayOption.add(OptionSupport("Quản lý đơn hàng",R.drawable.clock))
        arrayOption.add(OptionSupport("Quản lý bảo hành",R.drawable.notification))
        arrayOption.add(OptionSupport("Quản lý khách hàng",R.drawable.notification))
        arrayOption.add(OptionSupport("Quản lý sản phẩm",R.drawable.notification))
        arrayOption.add(OptionSupport("Chat với khách hàng",R.drawable.speak))
        arrayOption.add(OptionSupport("Đổi mật khẩu",R.drawable.reset_password))
        arrayOption.add(OptionSupport("Thống kê",R.drawable.reset_password))
        arrayOption.add(OptionSupport("Thông báo",R.drawable.reset_password))
        binding.listviewmanhinhchinh.adapter = OptionAdapter(this@MainAdminActivity,arrayOption)
        binding.nameUserCurrent.setText(DefaultFirst1.userCurrent.fullname.toString())
        // them sp moi
        // them sp moi

    }
    private fun inClick(){
        binding.btnDangXuat.setOnSafeClick {
            Firebase.auth.signOut()
            DefaultFirst1.userCurrent.role =0
            DefaultFirst1.userCurrent.user_id =0
            openActivity(SignInActivity::class.java, true)
        }
    }
    private fun getSpMoi() {
        var myRef : DatabaseReference = database.getReference("Products")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mangSpMoi.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val sanPhamMoi = postSnapshot.getValue(SanPhamMoi::class.java)
                    if(sanPhamMoi != null)
                    {
                        DefaultFirst1.mangSpConLai.add(sanPhamMoi)
                    }
                    if(sanPhamMoi != null&& sanPhamMoi.deleted ==1){
                        mangSpMoi.add(sanPhamMoi)

                    }
                }
                spAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })

        // all saleOrder
        var myRefAllSaleOrder : DatabaseReference = database.getReference("SaleOrders")
        myRefAllSaleOrder.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                DefaultFirst1.allSaleOrder.clear()
                for (postSnapshot in dataSnapshot.children) {
                    val sanPhamMoi = postSnapshot.getValue(SaleOrder::class.java)
                    if(sanPhamMoi != null){
                        DefaultFirst1.allSaleOrder.add(sanPhamMoi)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
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