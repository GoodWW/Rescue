package com.zyd.out.service.repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/*
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年10月16日 11:59
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * cniao数据库操作帮助类
 */
object CniaoDbHelper {

    /**
     * 获取room数据表中存储的userInfo
     * return liveData形式
     */
    fun getLiveUserInfo(context: Context) =
        CniaoDatabase.getInstance(context).userDao.queryLiveUser()

    /**
     * 以普通数据对象的形式，获取userInfo
     */
    fun getUserInfo(context: Context) = CniaoDatabase.getInstance(context).userDao.queryUser()

    /**
     * 删除数据表中的userInfo信息
     */
    fun deleteUserInfo(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            getUserInfo(context)?.let { info ->
                CniaoDatabase.getInstance(context).userDao.deleteUser(info)
            }
        }
    }

    /**
     * 新增用户数据到数据表
     */
    fun insertUserInfo(context: Context, user: CniaoUserInfo) {
        GlobalScope.launch(Dispatchers.IO) {
            CniaoDatabase.getInstance(context).userDao.insertUser(user)
        }
    }

}