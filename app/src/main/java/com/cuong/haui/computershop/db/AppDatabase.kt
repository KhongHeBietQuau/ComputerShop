package com.cuong.haui.computershop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cuong.haui.computershop.db.dao.UserDao
import com.cuong.haui.computershop.db.entity.*

@Database(
    version = 1,
    entities = [
        UserEntity::class,
        ComputerEntity::class,
        CategoryEntity::class,
        FeedBackEntity::class,
        OrderItemEntity::class,
        ProductImageEntity::class,
        ComputerSoldEntity::class,
        GuaranteeEntity::class,
        MaintenanceEntity::class,
        SaleOrderEntity::class,
        ShipperEntity::class,
        ShippingCompanyEntity::class

    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
