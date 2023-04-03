package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "FeedBack")
data class FeedBackEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "feed_back_id")
    var feed_back_id : Int,

    @ColumnInfo(name = "first_name")
    var first_name : String,

    @ColumnInfo(name = "last_name")
    var last_name : String,


    @ColumnInfo(name = "content")
    var content : String,


    @ColumnInfo(name = "user_id")
    var user_id : Int,

    @ColumnInfo(name = "product_id")
    var product_id : Int,
    @ColumnInfo(name = "create_at")
    var create_at : String,

    @ColumnInfo(name = "update_at")
    var update_at : String,


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

