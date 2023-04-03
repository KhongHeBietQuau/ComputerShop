package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "User")
data class UserEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
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

    @ColumnInfo(name = "password")
    var password : String,

    @ColumnInfo(name = "deleted_user")
    var deleted_user : Int,

    @ColumnInfo(name = "role_id")
    var role_id : Int,


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
