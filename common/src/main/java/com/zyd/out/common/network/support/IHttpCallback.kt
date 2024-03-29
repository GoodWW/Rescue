package com.zyd.out.common.network.support

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年08月18日 00:52
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 网络请求的接口回调
 */
interface IHttpCallback {

    /**
     * 网络请求成功的回调
     * [data] 返回回调的数据结果
     * @param data 返回回调的数据结果
     */
    fun onSuccess(data: Any?)


    /**
     * 接口回调失败
     * [error] 错误信息的数据类
     */
    fun onFailed(error: Any?)

}