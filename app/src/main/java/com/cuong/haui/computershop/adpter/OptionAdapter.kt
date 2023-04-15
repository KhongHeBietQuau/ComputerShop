package com.cuong.haui.computershop.adpter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.cuong.haui.computershop.R
import com.cuong.haui.computershop.model.OptionSupport

class OptionAdapter constructor(var context: Context,var mangmonan : ArrayList<OptionSupport>) :BaseAdapter() {
    class ViewHolder(row: View) {
        var textviewmonan: TextView
        var imageview: ImageView

        init {
            textviewmonan = row.findViewById(R.id.item_txt_name_option) as TextView
            imageview = row.findViewById(R.id.item_image_option) as ImageView
        }
    }

    override fun getCount(): Int {
        return mangmonan.size
    }

    override fun getItem(position: Int): Any {
        return mangmonan.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertview: View?, p2: ViewGroup?): View {
        var view: View?
        var viewholder: ViewHolder
        if (convertview == null) {
            var layoutinflater: LayoutInflater = LayoutInflater.from(context)
            view = layoutinflater.inflate(R.layout.item_sanpham, null)
            viewholder = ViewHolder(view)
            view.tag = viewholder
        } else {
            view = convertview
            viewholder = convertview.tag as ViewHolder
        }
        var monan: OptionSupport = getItem(position) as OptionSupport
        viewholder.textviewmonan.text = monan.ten
        viewholder.imageview.setImageResource(monan.hinhanh)
        return view as View
    }
}