package com.cuong.haui.computershop.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.AdapterView.OnItemClickListener
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
import com.cuong.haui.computershop.databinding.ActivityMainBinding
import com.cuong.haui.computershop.model.OptionSupport
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.laptopGaming.LaptopGamingActivity
import com.cuong.haui.computershop.ui.laptopOffice.LaptopOfficeActivity
import com.cuong.haui.computershop.ui.orderManagement.OrderManagementActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import com.cuong.haui.computershop.utils.ViewUtils
import com.cuong.haui.computershop.view.openActivity
import com.cuong.haui.computershop.view.setOnSafeClick
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MainActivity : BaseActivity<ActivityMainBinding>() {
    var database =FirebaseDatabase.getInstance()

    private lateinit var spAdapter : SanPhamMoiAdapter
    private var mangSpMoi  = ArrayList<SanPhamMoi>()

    override fun initCreate() {

        initData()

        inClick()
        ViewUtils.setCorners(resources,25f,binding.viewlipper)
        ActionBar()
        if (isConnected(this)) {
            Toast.makeText(applicationContext, "ok", Toast.LENGTH_LONG).show()
            Log.d("cuongnm", FirebaseAuth.getInstance().currentUser.toString())

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
        binding.listviewmanhinhchinh.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    val trangchu = Intent(applicationContext, MainActivity::class.java)
                    finish()
                    startActivity(trangchu)
                }
                1 -> {
                    val dienthoai = Intent(applicationContext, LaptopOfficeActivity::class.java)
                    finish()
                    startActivity(dienthoai)
                }
                2 -> {
                    val laptop = Intent(applicationContext, LaptopGamingActivity::class.java)
                    finish()
                    startActivity(laptop)
                }
                3 -> {
                    val laptop = Intent(applicationContext, OrderManagementActivity::class.java)
                    finish()
                    startActivity(laptop)
                }
                4 -> {
                    val laptop = Intent(applicationContext, CartActivity::class.java)
                    finish()
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
        //

            var totalItem = 0
            for (i in 0 until DefaultFirst1.manggiohang.size) {
                totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
            }
            binding.menuSl.setText(totalItem.toString())

        binding.framegiohang.setOnSafeClick {
            val giohang = Intent(applicationContext, CartActivity::class.java)
            startActivity(giohang)
        }

    }
    override fun onResume() {
        super.onResume()
        var totalItem = 0

        for (i in 0 until DefaultFirst1.manggiohang.size) {
            totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
        }

        binding.menuSl!!.setText(totalItem.toString())

    }
    private fun inClick(){
        binding.btnDangXuat.setOnSafeClick {
            Firebase.auth.signOut()
            openActivity(SignInActivity::class.java, true)
        }
    }
    private fun getSpMoi() {

        var myRef : DatabaseReference = database.getReference("Products")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val sanPhamMoi = postSnapshot.getValue(SanPhamMoi::class.java)
                    if(sanPhamMoi != null){
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
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        binding.recycleview.setLayoutManager(layoutManager)
        binding.recycleview.setHasFixedSize(true)
        spAdapter = SanPhamMoiAdapter(applicationContext, mangSpMoi)
        binding.recycleview.setAdapter(spAdapter)
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }


}