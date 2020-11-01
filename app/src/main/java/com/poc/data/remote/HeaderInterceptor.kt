package com.poc.data.remote

import com.poc.utility.AppConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .addHeader(AppConstants.KEY_AUTHORIZATION, AppConstants.CLIENT_ID)
            .build()
        return chain.proceed(request)
    }
}