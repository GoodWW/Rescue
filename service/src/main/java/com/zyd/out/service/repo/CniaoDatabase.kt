package com.zyd.out.service.repo

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年10月16日 11:41
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 菜鸟App的公共业务基础数据类
 */

//3、database
@Database(entities = [CniaoUserInfo::class], version = 1, exportSchema = false)
abstract class CniaoDatabase : RoomDatabase() {

    abstract val userDao: UserDao

    companion object {

        private const val CNIAO_DB_NAME = "cniao_db"

        @Volatile
        private var instance: CniaoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): CniaoDatabase {
            return instance ?: Room.databaseBuilder(
                context,
                CniaoDatabase::class.java,
                CNIAO_DB_NAME
            ).build().also { instance = it }
        }
    }
}

//1、entity的定义

@Entity(tableName = "tb_cniao_user")
data class CniaoUserInfo(
    @PrimaryKey
    val id: Int,//主键
    val course_permission: Boolean,
    val token: String?,
    @Embedded
    val user: User?
) {
    @Keep
    data class User(
        @ColumnInfo(name = "cniao_user_id")
        val id: Int,//用户id
        val logo_url: String?,//用户头像
        val reg_time: String?,//用户注册时间
        val username: String?//用户名
    )
}

//2、dao层的定义
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CniaoUserInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: CniaoUserInfo)

    @Delete
    fun deleteUser(user: CniaoUserInfo)

    // = , in , like
    @Query("select * from tb_cniao_user where id=:id")
    fun queryLiveUser(id: Int = 0): LiveData<CniaoUserInfo>

    @Query("select * from tb_cniao_user where id=:id")
    fun queryUser(id: Int = 0): CniaoUserInfo?

}


