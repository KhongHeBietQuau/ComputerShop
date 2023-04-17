package com.cuong.haui.computershop.ui.detail

import android.content.Intent
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.R
import com.bumptech.glide.Glide
import com.cuong.haui.computershop.base.BaseActivity
import com.cuong.haui.computershop.databinding.ActivityDetailBinding
import com.cuong.haui.computershop.model.GioHang
import com.cuong.haui.computershop.model.SanPhamMoi
import com.cuong.haui.computershop.ui.cart.CartActivity
import com.cuong.haui.computershop.utils.DefaultFirst1
import java.lang.String
import java.text.DecimalFormat
import kotlin.Long
import kotlin.arrayOf
import kotlin.let

class DetailActivity : BaseActivity<ActivityDetailBinding>() {

    var sanPhamMoi: SanPhamMoi? = null

    override fun initCreate() {
        initView()
        ActionToolBar()
        initData()
        initControl()
    }
    private fun initControl() {
        binding.btnthemvaogiohang!!.setOnClickListener { themGioHang() }
    }

    private fun themGioHang() {
        if (DefaultFirst1.manggiohang.size > 0) {
            var flag = false
            val soluong = binding.spinnerr!!.selectedItem.toString().toInt()
            for (i in 0 until DefaultFirst1.manggiohang.size) {
                if (DefaultFirst1.manggiohang.get(i).getIdsp() === sanPhamMoi?.product_id ?: 0) {
                    DefaultFirst1.manggiohang.get(i)
                        .setSoluong(soluong + DefaultFirst1.manggiohang.get(i).getSoluong())
                    val gia: Long =
                        (sanPhamMoi?.price_new?.toLong() ?: 0) * DefaultFirst1.manggiohang.get(i).getSoluong()
                    DefaultFirst1.manggiohang.get(i).setGiasp(gia)
                    Toast.makeText(applicationContext, "đang ấn", Toast.LENGTH_LONG).show()
                    flag = true
                }
            }
            if (flag == false) {
                val gia: Long = (sanPhamMoi?.price_new?.toLong() ?: 0) * soluong
                val gioHang = GioHang()
                gioHang.setGiasp(gia)
                gioHang.setSoluong(soluong)
                sanPhamMoi?.let { gioHang.setIdsp(it.product_id) }
                gioHang.setTensp(sanPhamMoi?.product_name ?: "")
                gioHang.setHinhsp(sanPhamMoi?.thumbnail_url ?: "")
                DefaultFirst1.manggiohang.add(gioHang)
            }
        } else {
            val soluong = binding.spinnerr!!.selectedItem.toString().toInt()
            val gia: Long = (sanPhamMoi?.price_new?.toLong() ?: 0) * soluong
            val gioHang = GioHang()
            gioHang.setGiasp(gia)
            gioHang.setSoluong(soluong)
            sanPhamMoi?.let { gioHang.setIdsp(it.product_id) }
            gioHang.setTensp(sanPhamMoi?.product_name ?: "")
            gioHang.setHinhsp(sanPhamMoi?.thumbnail_url ?: "")
            DefaultFirst1.manggiohang.add(gioHang)
        }
        var totalItem = 0
        for (i in 0 until DefaultFirst1.manggiohang.size) {
            totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
        }
        binding.menuSl!!.setText(totalItem.toString())
    }

    private fun initData() {
        sanPhamMoi = intent.getSerializableExtra("chitiet") as SanPhamMoi?
        binding.txttensp?.setText(sanPhamMoi?.product_name ?: "")
        binding.txtmotachitiet?.setText(sanPhamMoi?.description ?: "")
        binding.imgchitiet?.let { Glide.with(applicationContext).load(sanPhamMoi?.thumbnail_url).into(it) }
        val decimalFormat = DecimalFormat("###,###,###")
        binding.txtgiasp!!.text = "Gia: " + sanPhamMoi?.price_new?.let { decimalFormat.format(it.toDouble()) } + "Đ"
        val so = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val adapterspin = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, so)
        binding.spinnerr!!.adapter = adapterspin
    }

    private fun initView() {

        if (DefaultFirst1.manggiohang != null) {
            binding.menuSl.setText(String.valueOf(DefaultFirst1.manggiohang.size))
        }

        binding.framegiohang.setOnClickListener {
            val giohang = Intent(applicationContext, CartActivity::class.java)
            startActivity(giohang)
        }
    }

    private fun ActionToolBar() {
        setSupportActionBar(binding.toobar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.toobar!!.setNavigationOnClickListener { finish() }
    }

    override fun onResume() {
        super.onResume()
        var totalItem = 0

            for (i in 0 until DefaultFirst1.manggiohang.size) {
                totalItem = totalItem + DefaultFirst1.manggiohang.get(i).getSoluong()
            }
            
            binding.menuSl!!.setText(totalItem.toString())

    }
    override fun inflateViewBinding(inflater: LayoutInflater): ActivityDetailBinding {
        return ActivityDetailBinding.inflate(inflater)
    }
}