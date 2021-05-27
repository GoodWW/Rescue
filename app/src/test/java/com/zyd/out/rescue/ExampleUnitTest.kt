package com.zyd.out.rescue

import kotlinx.coroutines.*
import org.junit.Test

import kotlin.system.measureTimeMillis

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
//        testLaunch()
//        testAsync()
        testRunBlocking()
    }

    //阻塞当前函数的   通常用于普通函数和协程函数的桥接（目前不懂以后注意）
    //等待内部 的 协程执行完毕才会结束
    private fun testRunBlocking() {
        val time = measureTimeMillis {
            runBlocking {
                println("前     ${Thread.currentThread()}")
                delay(500)
                println("后     ${Thread.currentThread()}")
            }
            println("外面    ${Thread.currentThread()}")
        }
        println("总耗时    $time")
    }

    //launch   不阻塞线程  异步
    private fun testLaunch() {
        val time: Long = measureTimeMillis {
            GlobalScope.launch {
                Thread.sleep(1000)
                println("launch1   ${Thread.currentThread()}")
            }
            GlobalScope.launch {
                Thread.sleep(1000)
                println("launch2   ${Thread.currentThread()}")
            }
            println("两个launch外面   ${Thread.currentThread()}")
            Thread.sleep(2200)
        }
        println("函数总好事：   $time")
    }

    //async   不阻塞线程  异步
    private fun testAsync() {
        val time: Long = measureTimeMillis {
            GlobalScope.async {
                Thread.sleep(1000)
                println("launch1   ${Thread.currentThread()}")
            }
            GlobalScope.async {
                Thread.sleep(1000)
                println("launch2   ${Thread.currentThread()}")
            }
            println("两个launch外面   ${Thread.currentThread()}")
            Thread.sleep(2200)
        }
        println("函数总好事：   $time")
    }
}