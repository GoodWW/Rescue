package com.zyd.out.service.assistant

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.blankj.utilcode.util.ToastUtils
import com.zyd.out.service.R
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.zyd.out.common.utils.*

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年09月18日 06:50
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 用于配置切换不同的接口host，调试工具
 */
class ServerHostKit : AbstractKit() {

    override val icon = R.drawable.icon_server_host

    override val name = R.string.str_server_host_dokit


    override fun onAppInit(context: Context?) {
        //
    }

    private val hostMap = mapOf<String, String>(
        "开发环境Host" to HOST_DEV,
        "QA测试Host" to HOST_QA,
        "线上正式Host" to HOST_PRODUCT,
    )

    private val hosts = hostMap.values.toTypedArray()
    private val names = hostMap.keys.toTypedArray()

    override fun onClick(context: Context?) {
        val pos = hostMap.values.indexOf(getBaseHost()) % hostMap.size
        //弹窗，用于显示很选择不同的host配置
        context ?: return ToastUtils.showShort("~~ context null ~~")
        AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.str_server_host_dokit))
            .setSingleChoiceItems(
                names, pos
            ) { dialog, which ->
                setBaseHost(hosts[which])
                dialog.dismiss()
            }.show()
    }
}