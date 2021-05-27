package com.zyd.out.common.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.zyd.out.common.ktx.bindView
import com.zyd.out.common.ktx.viewLifeCycleOwner

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年09月12日 20:26
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * 简单封装的基类Activity
 */
abstract class BaseActivity<ActBinding : ViewDataBinding> : AppCompatActivity {
    /**
     * 无参构造函数
     */
    constructor() : super()

    /**
     * 可以填入layout布局的构造函数，使用viewBinding的方便
     * [layout] layout布局文件的id
     */
    constructor(@LayoutRes layout: Int) : super(layout)

    protected lateinit var mBinding: ActBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = bindView<ActBinding>(getLayoutRes()).also {
            it.lifecycleOwner = viewLifeCycleOwner
        }
        initView()
        initConfig()
        initData()
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    /**
     * 必要的view初始化
     */
    open fun initView() {
//        LogUtils.d("${this.javaClass.simpleName} 初始化 initView")
    }

    /**
     * 必要的配置初始化
     */
    open fun initConfig() {
//        LogUtils.d("${this.javaClass.simpleName} 初始化 initConfig")
    }

    /**
     * 必要的数据初始化
     */
    open fun initData() {
//        LogUtils.d("${this.javaClass.simpleName} 初始化 initData")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::mBinding.isInitialized) {
            mBinding.unbind()
        }
    }

    /**
     * 扩展用于liveData便捷写法的函数
     * [block]liveData对象，响应change变化的逻辑块
     */
    protected inline fun <T : Any> LiveData<T>.observeKt(crossinline block: (T?) -> Unit) {
        this.observe(this@BaseActivity, Observer {
            block(it)
        })
    }

}