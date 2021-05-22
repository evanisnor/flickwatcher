package com.evanisnor.moviereminder.cache

import android.content.Context
import dagger.BindsInstance
import dagger.Component


@Component(
    modules = [
        CacheModule::class
    ]
)
interface CacheComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): CacheComponent
    }

    fun getCache(): Cache

}