package com.evanisnor.flickwatcher.network.interceptors

import com.evanisnor.flickwatcher.network.NetworkModule
import com.evanisnor.flickwatcher.network.NetworkScope
import com.evanisnor.flickwatcher.network.TheMovieDbService
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@NetworkScope
class AuthorizationInterceptor @Inject constructor(
    @NetworkModule.TheMovieDbApiKey private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (!original.url().host().equals(TheMovieDbService.host) || apiKey.isBlank()) {
            return chain.proceed(original)
        }

        val request = original.newBuilder()
            .url(
                original.url().newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()
            )
            .build()

        return chain.proceed(request)
    }
}