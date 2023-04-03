package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ShippingCompany")
data class ShippingCompanyEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shipping_company_id")
    var shipping_company_id : Int,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "address")
    var address : String,

    @ColumnInfo(name = "corporate_tax_code")
    var corporate_tax_code : String,

    @ColumnInfo(name = "phone_number")
    var phone_number : String,

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
