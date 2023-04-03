package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "SaleOrder")
data class SaleOrderEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "sale_order_id")
    var sale_order_id : Int,

    @ColumnInfo(name = "price")
    var price : Int,

    @ColumnInfo(name = "quantity")
    var quantity : Int,

    @ColumnInfo(name = "total_money")
    var total_money : Int,

    @ColumnInfo(name = "status")
    var status : Int,

    @ColumnInfo(name = "create_at")
    var create_at : String,

    @ColumnInfo(name = "update_at")
    var update_at : String,

    @ColumnInfo(name = "shipper_id")
    var shipper_id : Int,

    @ColumnInfo(name = "user_id")
    var user_id : Int,

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
