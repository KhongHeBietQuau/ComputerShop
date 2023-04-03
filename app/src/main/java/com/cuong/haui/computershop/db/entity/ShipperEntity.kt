package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Shipper")
data class ShipperEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shipper_id")
    var user_id : Int,

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

    @ColumnInfo(name = "citizen_identification_code")
    var citizen_identification_code : String,

    @ColumnInfo(name = "age")
    var age : String,

    @ColumnInfo(name = "thumbnail_url")
    var thumbnail_url : String,

    @ColumnInfo(name = "shipping_company_id")
    var shipping_company_id : Int,

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
