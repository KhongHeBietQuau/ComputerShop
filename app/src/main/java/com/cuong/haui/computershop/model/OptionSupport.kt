package com.cuong.haui.computershop.model

data class OptionSupport (var ten : String,var hinhanh:Int){
    override fun toString(): String {
        return "OptionSupport(ten='$ten', hinhanh=$hinhanh)"
    }
}