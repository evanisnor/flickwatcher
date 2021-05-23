package com.evanisnor.moviereminder.libraries.maincomponent

import com.evanisnor.moviereminder.cache.CacheComponent
import dagger.Component

@ApplicationScope
@Component(
    dependencies = [
        CacheComponent::class
    ]
)
interface MainComponent {

    @Component.Builder
    interface Builder {

        fun cacheComponent(cacheComponent: CacheComponent): Builder

        fun build(): MainComponent
    }

    fun cacheComponent(): CacheComponent

}