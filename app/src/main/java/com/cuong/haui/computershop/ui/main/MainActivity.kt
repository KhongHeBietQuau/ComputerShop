package com.cuong.haui.computershop.ui.main

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
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
import com.cuong.haui.computershop.model.User
import com.cuong.haui.computershop.ui.bookWarranty.BookWarrantyActivity
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.ui.chatMain.ChatMainActivity
import com.cuong.haui.computershop.ui.laptopGaming.LaptopGamingActivity
import com.cuong.haui.computershop.ui.laptopOffice.LaptopOfficeActivity
import com.cuong.haui.computershop.ui.listProductWarranty.ListProductWarrantyActivity
import com.cuong.haui.computershop.ui.orderManagement.OrderManagementActivity
import com.cuong.haui.computershop.ui.resetPassword.ResetPasswordActivity
import com.cuong.haui.computershop.ui.signIn.SignInActivity
import com.cuong.haui.computershop.ui.warrantyManagement.warrantyManagementActivity
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

        SelectUserCurrent()
        initData()

        inClick()
        ViewUtils.setCorners(resources,25f,binding.viewlipper)
        ActionBar()
        if (isConnected(this)) {
            //Toast.makeText(applicationContext, "ok", Toast.LENGTH_LONG).show()
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

    private fun SelectUserCurrent() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            var myRef : DatabaseReference = database.getReference("Users").child(name.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    DefaultFirst1.userCurrent = dataSnapshot.getValue(User::class.java)
                    //Toast.makeText(applicationContext, DefaultFirst1.userCurrent.toString() , Toast.LENGTH_LONG).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

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

                    startActivity(trangchu)
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
                    val laptop = Intent(applicationContext, OrderManagementActivity::class.java)

                    startActivity(laptop)
                }
                4 -> {
                    val laptop = Intent(applicationContext, CartActivity::class.java)
                    startActivity(laptop)
                }
                5 -> {
                    val laptop = Intent(applicationContext, ListProductWarrantyActivity::class.java)

                    startActivity(laptop)
                }
                6 -> {
                    val laptop = Intent(applicationContext, warrantyManagementActivity::class.java)

                    startActivity(laptop)
                }
                7 -> {
                    val laptop = Intent(applicationContext, ChatMainActivity::class.java)

                    startActivity(laptop)
                }
                8 -> {
                    val phone_number = "+84́862816585"
                    val phone_intent = Intent(Intent.ACTION_CALL)
                    phone_intent.data = Uri.parse("tel:$phone_number")
                    startActivity(phone_intent)
                }
                9 -> {
                    val laptop = Intent(applicationContext, ResetPasswordActivity::class.java)
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
        //userCurrent = intent.getSerializableExtra("userCurrent") as User?
        var arrayOption : ArrayList<OptionSupport> = ArrayList()
        arrayOption.add(OptionSupport("Trang chủ",R.drawable.house))
        arrayOption.add(OptionSupport("Laptop văn phòng",R.drawable.laptop))
        arrayOption.add(OptionSupport("Laptop cấu hình cao",R.drawable.laptop_gaming))
        arrayOption.add(OptionSupport("Theo dõi đơn hàng",R.drawable.clock))
        arrayOption.add(OptionSupport("Đơn nháp",R.drawable.draft))
        arrayOption.add(OptionSupport("Đặt lịch bảo hành",R.drawable.guarantee))
        arrayOption.add(OptionSupport("Theo dõi bảo hành",R.drawable.notification))
        arrayOption.add(OptionSupport("Chat với nhân viên",R.drawable.speak))
        arrayOption.add(OptionSupport("Gọi điện",R.drawable.phone))
        arrayOption.add(OptionSupport("Đổi mật khẩu",R.drawable.reset_password))
        binding.listviewmanhinhchinh.adapter = OptionAdapter(this@MainActivity,arrayOption)
        // set Ten nguoi dung
        binding.nameUserCurrent.setText(DefaultFirst1.userCurrent.fullname.toString())
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
        // lấy user đang dùng
        val user = Firebase.auth.currentUser
        user?.let {
            val name = it.displayName
            val email = it.email
            var myRef : DatabaseReference = database.getReference("Users").child(name.toString())
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                        DefaultFirst1.userCurrent = dataSnapshot.getValue(User::class.java)
                    //Toast.makeText(applicationContext, DefaultFirst1.userCurrent.toString() , Toast.LENGTH_LONG).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w("abc", "loadPost:onCancelled", databaseError.toException())
                    // ...
                }
            })

        }

        var totalItem = 0

        for (i in 0 until DefaultFirst1.manggiohang.size) {
            totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
        }

        binding.menuSl!!.setText(totalItem.toString())

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
                    if(sanPhamMoi != null && sanPhamMoi.deleted ==1){
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