package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "OrderItem")
data class OrderItemEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_item_id")
    var order_item_id : Int,

    @ColumnInfo(name = "first_name")
    var first_name : String,

    @ColumnInfo(name = "last_name")
    var last_name : String,

    @ColumnInfo(name = "email")
    var email : String,

    @ColumnInfo(name = "phone_number")
    var phone_number : String,

    @ColumnInfo(name = "address")
    var address : String,

    @ColumnInfo(name = "note")
    var note : String,

    @ColumnInfo(name = "order_date")
    var order_date : String,

    @ColumnInfo(name = "total_money")
    var total_money : Int,

    @ColumnInfo(name = "quantity")
    var quantity : Int,

    @ColumnInfo(name = "product_id")
    var product_id : Int,

    @ColumnInfo(name = "sale_order_id")
    var sale_order_id : Int,

    ) : Parcelable {

    companion object {
        fun toUser(jsonData: String): ComputerEntity? {
            return Gson().fromJson(jsonData, ComputerEntity::class.java)
        }
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}
