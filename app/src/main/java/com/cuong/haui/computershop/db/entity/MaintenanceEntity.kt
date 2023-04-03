package com.cuong.haui.computershop.db.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize



@Parcelize
@Entity(tableName = "Maintenance")
data class MaintenanceEntity @JvmOverloads constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "maintenance_id")
    var maintenance_id : Int,

    @ColumnInfo(name = "problem")
    var problem : String,

    @ColumnInfo(name = "computer_sold_id")
    var computer_sold_id : Int,

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