package com.zyd.out.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月27日 14:51
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * viewModel的公共基类
 */
abstract class BaseViewModel : ViewModel() {

    private val jobs = mutableListOf<Job>()
    val isLoading = MutableLiveData<Boolean>()//标记网络loading状态

    /**
     * 协程 网络请求
     */
    protected fun serverAwait(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        isLoading.value = true
        block.invoke(this)
        isLoading.value = false
    }.addTo(jobs)


    override fun onCleared() {
        jobs.forEach { it.cancel() }
        super.onCleared()
    }
}

/**
 * 扩展函数，用于viewModel中的job 添加到list方便
 */
private fun Job.addTo(list: MutableList<Job>) {
    list.add(this)
}