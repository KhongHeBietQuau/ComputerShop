package com.cuong.haui.computershop.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cuong.haui.computershop.db.dao.UserDao
import com.cuong.haui.computershop.db.entity.*
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val userDao: UserDao) {

    //User
//    fun getUser() = userDao.getUser()
//    fun getNameUser() = userDao.getNameUser()
//    fun getLevelUser() = userDao.getNameUser()
//    fun getExpUser() = userDao.getNameUser()
//    fun getGoldUser() = userDao.getNameUser()
//    fun getDiamondUser() = userDao.getNameUser()
//    fun getAvtUser() = userDao.getNameUser()
//    fun addUser(data: UserEntity) = userDao.addUser(data)


}