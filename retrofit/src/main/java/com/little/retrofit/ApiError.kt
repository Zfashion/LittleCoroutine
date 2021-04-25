package com.little.retrofit

object ApiError {

    //请求，返回的数据为null
    val dataIsNull = Error(-1, "data is null")

    //http status code 不成功
    val httpStatusCodeError = Error(-2, "Server error. Please try again latter.")

    //未知异常
    val unknownException = Error(-3, "unknown exception")
}

data class Error(val errorCode: Int, val errorMsg: String)