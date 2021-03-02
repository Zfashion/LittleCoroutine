package com.example.little.utils

import java.util.*


object IDUtils {

    fun uniqueCodeWithUUID(): String? {
        return UUID.randomUUID().toString()
    }

    fun uniqueCodeWithNano(): String? {
        val n = System.nanoTime()
        return n.toString()
    }

}