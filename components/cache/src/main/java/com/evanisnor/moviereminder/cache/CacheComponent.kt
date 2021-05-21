package com.evanisnor.moviereminder.cache

import dagger.Component


@Component(
    modules = [
        CacheModule::class
    ]
)
interface CacheComponent {

    fun getCache(): Cache

}