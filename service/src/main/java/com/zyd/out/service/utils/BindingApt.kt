package com.zyd.out.service.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.zyd.out.service.R

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年10月08日 14:47
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 项目适配用的BindAdapter
 */


/**
 * imageView支持图片加载 绑定
 */
@BindingAdapter("app:srcCompat", requireAll = false)
fun imgSrc(iv: ImageView, src: Any?) {

    val imgRes = when (src) {
        is String -> {
            when {
                src.startsWith("//img.cniao5.com") -> "https:$src"
                src.startsWith("/img.cniao5.com") -> "https:/$src"
                else -> src
            }
        }
        else -> src ?: R.drawable.icon_default_header
    }
    Glide.with(iv)
        .load(imgRes)
        .into(iv)
}

/**
 * 图片资源支持tint颜色修改，支持colorRes和colorInt形式
 */
@BindingAdapter("app:tint")
fun imgColor(iv: ImageView, color: Int) {
    if (color == 0) return
    runCatching {
        iv.setColorFilter(iv.resources.getColor(color))
    }.onFailure {
        iv.setColorFilter(color)
    }
}

/**
 * textColor的binding形式，支持colorRes和colorInt形式
 */
@BindingAdapter("android:textColor")
fun tvColor(tv: TextView, color: Int) {
    if (color == 0) return
    runCatching {
        tv.setTextColor(tv.resources.getColor(color))
    }.onFailure {
        tv.setTextColor(color)
    }
}

