package com.zyd.out.common.widget

import android.view.MenuItem
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年09月26日 03:50
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * BottomNavigationView和ViewPager2关联的中介者
 */
class BnvMediator(
    private val bnv: BottomNavigationView,
    private val vp2: ViewPager2,
    private val config: ((bnv: BottomNavigationView, vp2: ViewPager2) -> Unit)? = null
) {
    //存储bottomNavigationView的menu的item和其自身position的对应关系
    private val map = mutableMapOf<MenuItem, Int>()

    init {
        //初始化bnv的item和index对应关系
        bnv.menu.forEachIndexed { index, item ->
            map[item] = index
        }
    }

    /**
     * 关联BottomNavigationView和ViewPager2的选择关系
     */
    fun attach() {
        config?.invoke(bnv, vp2)
        vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bnv.selectedItemId = bnv.menu[position].itemId
            }
        })
        bnv.setOnNavigationItemSelectedListener { item ->
            vp2.currentItem = map[item] ?: error("没有对应${item.title}的ViewPager2的元素")
            true
        }
    }
}
