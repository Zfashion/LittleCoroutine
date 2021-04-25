package com.little.retrofit.client

import com.little.base.BuildConfig
import com.little.ext.app
import com.little.retrofit.call_adapter.ApiResultCallAdapterFactory
import com.little.retrofit.interceptor.BusinessErrorInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


private const val BASE_URL = "http://fanyi.youdao.com/"

private val client: OkHttpClient by lazy { newClient() }

val retrofit = Retrofit.Builder()
    .client(client)
    .addCallAdapterFactory(ApiResultCallAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


private const val TIME_OUT = 30L
private const val READ_TIME_OUT = 10L
private const val WRITE_TIME_OUT = 10L

private fun newClient(): OkHttpClient =
    OkHttpClient
        .Builder()
        .apply {
            val httpCacheDir = File(app.cacheDir, "http-cache")
            val cacheSize = 10 * 1024 * 1024L
            val cache = Cache(httpCacheDir, cacheSize)

            connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
            val logging = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) logging.level = HttpLoggingInterceptor.Level.BODY
            else logging.level = HttpLoggingInterceptor.Level.BASIC
            addInterceptor(logging)
            addInterceptor(BusinessErrorInterceptor())
//            addInterceptor(SmartBoosterInterceptor()) //加解密过滤器
            retryOnConnectionFailure(true)
            cache(cache)
        }.build()