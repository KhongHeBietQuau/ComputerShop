package com.cuong.haui.computershop.utils

import android.content.res.Resources
import android.graphics.Outline
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider

object ViewUtils {
    fun setCorners(resources: Resources, radius: Float, view : View) {
        val mOutlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                val left = 0
                val top = 0
                val right = view.width
                val bottom = view.height
                val cornerRadiusDP = radius
                val cornerRadius = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    cornerRadiusDP,
                    resources.displayMetrics
                ).toInt()
                outline.setRoundRect(left, top, right, bottom, cornerRadius.toFloat())
            }
        }
        view.apply {
            outlineProvider = mOutlineProvider
            clipToOutline = true
        }
    }
}