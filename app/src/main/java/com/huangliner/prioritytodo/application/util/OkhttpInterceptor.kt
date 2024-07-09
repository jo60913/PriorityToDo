package com.huangliner.prioritytodo.application.util

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber

class OkhttpInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request().newBuilder()
            .build()
        if (request.method == "POST" || request.method == "PUT") {
            val requestBody = request.body
            if (requestBody != null) {
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val requestBodyString = buffer.readUtf8()
                Timber.e("網路請求 Request Body: $requestBodyString")
            }
        }

        // 列印網路請求回傳的內容
        val response: Response = chain.proceed(request)
        val jsonData = response.body?.string()
        Timber.e("網路請求 Response: ${response.toString()} JSON內容$jsonData")

        val newResponse = response.newBuilder()
            .body(ResponseBody.create(response.body?.contentType(), jsonData!!))
            .build()

        return newResponse
    }

}