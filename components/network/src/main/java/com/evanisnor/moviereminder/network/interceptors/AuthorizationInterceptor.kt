package com.evanisnor.moviereminder.network.interceptors

import com.evanisnor.moviereminder.network.NetworkScope
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@NetworkScope
class AuthorizationInterceptor @Inject constructor() : Interceptor {

    private val key = "432d78112fc4211c54e9a018d23915cb"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request = original.newBuilder()
            .url(
                original.url().newBuilder()
                    .addQueryParameter("api_key", key)
                    .build()
            )
            .build()

        return chain.proceed(request)
    }
}