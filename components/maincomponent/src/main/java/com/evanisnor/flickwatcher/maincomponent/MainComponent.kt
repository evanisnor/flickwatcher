package com.evanisnor.flickwatcher.maincomponent

import com.evanisnor.flickwatcher.cache.CacheComponent
import com.evanisnor.flickwatcher.network.NetworkComponent
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
    fun networkComponent(): NetworkComponent

}