package com.example.little

import android.os.Looper
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun coroutine_test() {
//        val mainThread = Looper.getMainLooper().thread
        GlobalScope.launch {
//            val isMainThread =  mainThread == Thread.currentThread()
//            println("is Main thread $isMainThread")
            delay(1000L)
            println("World!")
        }
        println("Hello")
        Thread.sleep(2000L)
    }

    @Test
    fun coroutine_cancel() = runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) { // computation loop, just wastes CPU
                // print a message twice a second
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion 直到job任务完成，父协程恢复执行
        println("main: Now I can quit.")
    }

    @Test
    fun coroutine_cancel2() = runBlocking {
        val job = launch(Dispatchers.Default) {
            try {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            println("job: I'm running finally")
        }
        }
        delay(1300L) // delay a bit
        println("main: I'm tired of waiting!")
        job.cancelAndJoin() // cancels the job and waits for its completion 直到job任务完成，父协程恢复执行
        println("main: Now I can quit.")
    }
}