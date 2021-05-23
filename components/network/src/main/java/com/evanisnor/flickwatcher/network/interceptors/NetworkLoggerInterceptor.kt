package com.evanisnor.flickwatcher.network.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkLoggerInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.i("Network", request.toString())
        val response = chain.proceed(request)
        Log.i("Network", response.toString())
        return response
    }
}