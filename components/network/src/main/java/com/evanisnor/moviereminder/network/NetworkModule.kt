package com.evanisnor.moviereminder.network

import com.evanisnor.moviereminder.network.interceptors.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
object NetworkModule {

    @Provides
    fun okHttpClient(
        authorizationInterceptor: AuthorizationInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(authorizationInterceptor)
        .build()

    @Provides
    fun moshiConverterFactory() = MoshiConverterFactory.create()

    @Provides
    fun theMovieDbService(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): TheMovieDbService = Retrofit.Builder()
        .baseUrl(TheMovieDbService.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()
        .create(TheMovieDbService::class.java)
}