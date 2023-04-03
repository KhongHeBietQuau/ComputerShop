package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "ProductImage")
data class ProductImageEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_image_id")
    var product_image_id : Int,

    @ColumnInfo(name = "image_url")
    var image_url : String,

    @ColumnInfo(name = "create_at")
    var create_at : String,

    @ColumnInfo(name = "update_at")
    var update_at : String,

    @ColumnInfo(name = "product_id")
    var product_id : Int,

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

