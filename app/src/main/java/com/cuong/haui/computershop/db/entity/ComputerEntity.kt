package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "Product")
data class ComputerEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    var product_id : Int,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "price_new")
    var price_new : Int,

    @ColumnInfo(name = "price_old")
    var price_old : Int,

    @ColumnInfo(name = "thumbnail_url")
    var thumbnail_url : String,

    @ColumnInfo(name = "description")
    var description : String,

    @ColumnInfo(name = "cpu")
    var cpu : Int,

    @ColumnInfo(name = "ram")
    var ram : Int,

    @ColumnInfo(name = "hard_drive")
    var hard_drive : Int,

    @ColumnInfo(name = "graphics_card")
    var graphics_card : String,

    @ColumnInfo(name = "screen")
    var screen : String,

    @ColumnInfo(name = "precent_discount")
    var precent_discount : Int,

    @ColumnInfo(name = "create_at")
    var create_at : String,

    @ColumnInfo(name = "update_at")
    var update_at : String,

    @ColumnInfo(name = "deleted_product")
    var deleted_product : Int,

    @ColumnInfo(name = "current_quanity")
    var current_quanity : Int,

    @ColumnInfo(name = "warranty_period")
    var warranty_period : Int,

    @ColumnInfo(name = "category_id")
    var category_id : Int,


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