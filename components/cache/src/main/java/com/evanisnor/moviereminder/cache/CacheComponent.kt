package com.evanisnor.moviereminder.cache

import dagger.Component

@CacheScope
@Component
interface CacheComponent {

    fun getCache(): Cache

}