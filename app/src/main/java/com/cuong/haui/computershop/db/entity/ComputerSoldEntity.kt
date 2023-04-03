package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize



@Parcelize
@Entity(tableName = "ComputerSold")
data class ComputerSoldEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "computer_sold_id")
    var computer_sold_id : Int,

    @ColumnInfo(name = "serial")
    var serial : String,

    @ColumnInfo(name = "create_at")
    var create_at : String,

    @ColumnInfo(name = "update_at")
    var update_at : String,

    @ColumnInfo(name = "sale_order_id")
    var sale_order_id : Int,

    @ColumnInfo(name = "start_warranty_period")
    var start_warranty_period : String,

    @ColumnInfo(name = "end_warranty_period")
    var end_warranty_period : String,
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
