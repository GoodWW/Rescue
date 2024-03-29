package com.zyd.out.common.network.support

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zyd.out.common.network.model.ApiResponse
import com.zyd.out.common.network.model.DataResult
import com.zyd.out.common.network.model.UNKNOWN_ERROR_CODE
import com.google.gson.Gson
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import retrofit2.await
import retrofit2.awaitResponse
import java.io.IOException

/**
 * 作者： 志威  zhiwei.org
 * 主页： Github: https://github.com/zhiwei1990
 * 日期： 2020年04月28日 12:08
 * 签名： 天行健，君子以自强不息；地势坤，君子以厚德载物。
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/  -- 志威 zhiwei.org
 *
 * You never know what you can do until you try !
 * ----------------------------------------------------------------
 * OkHttp3 的 Response的扩展函数，属性
 */


//region okHttp3相关扩展
/**
 * okHttp的Call执行异步，并转化为liveData可观察结果
 */
inline fun <reified T> okhttp3.Call.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: okhttp3.Call, e: IOException) {
            live.postValue(null)
        }

        override fun onResponse(call: okhttp3.Call, response: Response) {
            if (response.isSuccessful) {
                response.toEntity<T>()
            }
        }

    })
    return live
}


/**
 * 将Response的对象，转化为需要的对象类型，也就是将body.string转为entity
 * @return 返回需要的类型对象，可能为null，如果json解析失败的话
 * reified  关键字修饰才能知道具体类型  T::class.java
 */
inline fun <reified T> Response.toEntity(): T? {
    if (!isSuccessful) return null
    //gson不允许我们将json对象采用String,所以单独处理
    if (T::class.java.isAssignableFrom(String::class.java)) {
        return kotlin.runCatching {
            this.body?.string()
        }.getOrNull() as? T
    }
    return kotlin.runCatching {
        Gson().fromJson(this.body?.string(), T::class.java)
    }.onFailure { e ->
        e.printStackTrace()
    }.getOrNull()
}
//endregion


//region retrofit 相关扩展
/**
 * 第一种返回数 LiveData
 * Retrofit的Call执行异步，并转化为liveData可观察结果
 */
fun <T : Any> Call<T>.toLiveData(): LiveData<T?> {
    val live = MutableLiveData<T?>()
    this.enqueue(object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) {
            live.postValue(null)
        }

        override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
            val value = if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
            live.postValue(value)
        }
    })
    return live
}


/**
 *   第二种返回数据结构不同
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return DataResult 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverData(): DataResult<T> {
    var result: DataResult<T> = DataResult.Loading
    kotlin.runCatching {
        this.await()
    }.onFailure {
        result = DataResult.Error(RuntimeException(it))
        it.printStackTrace()
    }.onSuccess {
        result = DataResult.Success(it)
    }
    return result
}


/**
 *   第三种返回数据结构不同
 * 扩展retrofit的返回数据，调用await，并catch超时等异常
 * @return ApiResponse 返回格式为ApiResponse封装
 */
suspend fun <T : Any> Call<T>.serverRsp(): ApiResponse<T> {
    var result: ApiResponse<T>
    val response = kotlin.runCatching {
        this.awaitResponse()
    }.onFailure {
        result = ApiResponse.create(UNKNOWN_ERROR_CODE, it)
        it.printStackTrace()
    }.getOrThrow()
    result = ApiResponse.create(response)
    return result
}
//endregion
