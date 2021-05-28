package com.zyd.out.common.ktx

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.app.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年09月12日 20:05
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * Activity相关的Ktx ，扩展函数和扩展属性
 */

//region     扩展函数
/**
 * Activity  中   使用   DataBinding   时   setContentView  的  简化
 * [layout] 布局文件
 * @return 返回一个Binding的对象实例
 */
fun <T : ViewDataBinding> Activity.bindView(@LayoutRes layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

/**
 * Activity   中    使用D   ataBinding   时   setContentView   的  简化
 * [layout] 布局文件
 * @return 返回一个Binding的对象实例 T 类型的 可null的
 */
fun <T : ViewDataBinding> Activity.bindView(view: View): T? {
    return DataBindingUtil.bind<T>(view)
}

/**
 * 界面  Activity  的  沉浸式状态栏，使得可以在  状态栏   里面  显示  部分  需要的图片
 * 注意点，需要在  setContentView  之前   调用该函数   才生效
 */
fun Activity.immediateStatusBar() {
    window.apply {
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}

/**
 * 软键盘   的    隐藏
 * [view] 事件控件View
 */
fun Activity.dismissKeyBoard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager?
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}
//endregion




//region 扩展属性
/**
 * 扩展lifeCycleOwner属性，便于和Fragment之间使用lifeCycleOwner 一致性
 */
val ComponentActivity.viewLifeCycleOwner: LifecycleOwner
    get() = this

/**
 * Activity的扩展字段，便于和Fragment中使用liveData之类的时候，参数一致性
 */
val Activity.context: Context
    get() = this
//endregion



 