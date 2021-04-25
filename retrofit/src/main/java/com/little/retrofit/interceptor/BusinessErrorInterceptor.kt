package com.little.retrofit.interceptor

import android.util.Log
import com.little.retrofit.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets.UTF_8

class BusinessErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val response = chain.proceed(chain.request())

        //http status不是成功的情况下，不处理
        if (response.isSuccessful.not()) return response

        //因为response.body().string() 只能调用一次，所以这里读取responseBody不使用response.body().string()
        val responseBody = response.body!!
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) //  Buffer the entire body.
        val buffer = source.buffer
        val contentType = responseBody.contentType()
        val charset : Charset = contentType?.charset(UTF_8) ?: UTF_8
        val resultString = buffer.clone().readString(charset)
        Log.i("http result string: ", resultString)

        val jsonObject = JSONObject(resultString)
        if (jsonObject.has("errorCode").not()) return response

        val errorCode = jsonObject.optInt("errorCode")
        //对于业务成功的情况不做处理
        if (errorCode == 0) {
            return response
        }
        throw ApiException(errorCode, "some error msg")
    }

}