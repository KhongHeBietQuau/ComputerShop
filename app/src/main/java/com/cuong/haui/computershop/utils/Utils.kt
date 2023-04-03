package com.cuong.haui.computershop.utils

import android.app.Activity
import android.os.Build
import androidx.core.content.ContextCompat

object Utils {
    fun checkSelfPermission(activity: Activity?, s: String?): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            ContextCompat.checkSelfPermission(activity!!, s!!) == 0
        } else {
            true
        }
    }
 }