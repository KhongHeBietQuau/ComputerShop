package com.cuong.haui.computershop.model

data class Products(val product_id: Int,val product_name:String?="",val price_new: Int,val price_old: Int,val thumbnail_url:String?=""
               ,val description:String?="",val cpu: String?="",val ram: String?="",val hard_drive: String?="",val graphics:String?="",
               val screen:String?="",val precent_discount: Int,val created_at:String?="",val update_at:String?="",val deleted: Int,
               val current_quantity: Int,val warranty_period: Int,val category_id: Int) {
}