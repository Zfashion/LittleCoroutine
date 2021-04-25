package com.little.retrofit

import okio.IOException

class ApiException(val errorCode: Int, val errorMsg: String): IOException()