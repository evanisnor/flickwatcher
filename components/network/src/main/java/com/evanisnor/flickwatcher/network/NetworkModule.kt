package com.evanisnor.flickwatcher.network

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import coil.util.CoilUtils
import com.evanisnor.flickwatcher.network.interceptors.AuthorizationInterceptor
import com.evanisnor.flickwatcher.network.interceptors.NetworkLoggerInterceptor
import com.evanisnor.flickwatcher.network.interceptors.TrafficStatsInterceptor
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier

@Module
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class TheMovieDbApiKey

    @Provides
    @NetworkScope
    @TheMovieDbApiKey
    fun theMovieDbApiKey(context: Context) =
        context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData.getString("com.evanisnor.flickwatcher.apikey.themoviedb") ?: ""

    @Provides
    @NetworkScope
    fun dispatcher() = Dispatchers.IO

    @Provides
    @NetworkScope
    fun imageCache(context: Context) = CoilUtils.createDefaultCache(context)

    @Provides
    @NetworkScope
    fun okHttpClient(
        trafficStatsInterceptor: TrafficStatsInterceptor,
        networkLoggerInterceptor: NetworkLoggerInterceptor,
        authorizationInterceptor: AuthorizationInterceptor,
        imageCache: Cache
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(trafficStatsInterceptor)
        .addNetworkInterceptor(networkLoggerInterceptor)
        .addNetworkInterceptor(authorizationInterceptor)
        .cache(imageCache)
        .build()

    @Provides
    @NetworkScope
    fun moshiConverterFactory(): MoshiConverterFactory = MoshiConverterFactory.create()

    @Provides
    @NetworkScope
    fun theMovieDbService(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): TheMovieDbService = Retrofit.Builder()
        .baseUrl(TheMovieDbService.baseUrl)
        .client(okHttpClient)
        .addConverterFactory(moshiConverterFactory)
        .build()
        .create(TheMovieDbService::class.java)

    @Provides
    @NetworkScope
    fun connectivityManager(context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
}