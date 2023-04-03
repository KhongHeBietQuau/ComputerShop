package com.cuong.haui.computershop.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cuong.haui.computershop.db.entity.*

@Dao
interface UserDao {

/*    //User
    @Query("SELECT * FROM user")
    fun getUser(): LiveData<MutableList<UserEntity>>

    @Query("SELECT name_user FROM user WHERE id = 1")
    fun getNameUser(): LiveData<String>

    @Query("SELECT level_user FROM user WHERE id = 1")
    fun getLevelUser(): LiveData<Long>

    @Query("SELECT exp_user FROM user WHERE id = 1")
    fun getExpUser(): LiveData<Long>

    @Query("SELECT gold_user FROM user WHERE id = 1")
    fun getGoldUser(): LiveData<Long>

    @Query("SELECT diamond_user FROM user WHERE id = 1")
    fun getDiamondUser(): LiveData<Long>

    @Query("SELECT avt_user FROM user WHERE id = 1")
    fun getAvtUser(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(data: UserEntity): Long

    //General Card
    @Query("SELECT * FROM general_card")
    fun getAllGeneralCard() : LiveData<MutableList<GeneralCardEntity>>

    @Query("SELECT * FROM general_card WHERE have = 1")
    fun getAllGeneralCardOfUser() : LiveData<MutableList<GeneralCardEntity>>

    @Query("SELECT * FROM general_card WHERE use = 1")
    fun getAllGeneralCardOfUserUse() : LiveData<MutableList<GeneralCardEntity>>

    @Query("SELECT * FROM general_card WHERE use = 0")
    fun getAllGeneralCardOfUserNoUse() : LiveData<MutableList<GeneralCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListDataGeneralCard(data: List<GeneralCardEntity>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateGeneralCard(data: GeneralCardEntity) : Long

    //Help Card
    @Query("SELECT * FROM help_card")
    fun getAllHelpCard() : LiveData<MutableList<HelpCardEntity>>

    @Query("SELECT * FROM help_card WHERE have = 1")
    fun getAllHelpCardOfUser() : LiveData<MutableList<HelpCardEntity>>

    @Query("SELECT * FROM help_card WHERE use = 1")
    fun getAllHelpCardOfUserUse() : LiveData<MutableList<HelpCardEntity>>

    @Query("SELECT * FROM help_card WHERE use = 0")
    fun getAllHelpCardOfUserNoUse() : LiveData<MutableList<HelpCardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateHelpCard(data: HelpCardEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListDataHelpCard(data: List<HelpCardEntity>): List<Long>*/

}
