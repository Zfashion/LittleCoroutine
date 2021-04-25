package com.little.retrofit.api

import com.little.retrofit.ApiResult
import com.little.retrofit.client.retrofit
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface TranslateApiService {
    @FormUrlEncoded
    @POST("translate?doctype=json")
    suspend fun translate(@Field("i") i: String): ApiResult<Result>
}

data class Result(
    val type: String,
    val elapsedTime: Int,
    val translateResult: List<List<TranslateResult>>
) {
    data class TranslateResult(
        val src: String,
        val tgt: String
    )
}

object TranslateApi {
    val retrofitService: TranslateApiService by lazy { retrofit.create(TranslateApiService::class.java) }
}