package com.evanisnor.moviereminder.network.interceptors

import android.net.TrafficStats
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class TrafficStatsInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        TrafficStats.setThreadStatsTag(Thread.currentThread().id.toInt())
        return chain.proceed(chain.request())
    }

}