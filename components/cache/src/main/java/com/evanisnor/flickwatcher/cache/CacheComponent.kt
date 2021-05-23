package com.evanisnor.flickwatcher.cache

import android.content.Context
import com.evanisnor.flickwatcher.network.DaggerNetworkComponent
import com.evanisnor.flickwatcher.network.NetworkComponent
import dagger.BindsInstance
import dagger.Component

@CacheScope
@Component(
    dependencies = [
        NetworkComponent::class
    ],
    modules = [
        CacheModule::class
    ]
)
interface CacheComponent {

    companion object {

        // Hack create method.
        // Exists to avoid requiring a gradle dependency on the Network module.
        // Necessary because this component has a Builder (not a no-arg component)
        fun create(context: Context) = DaggerCacheComponent.builder()
            .context(context)
            .networkComponent(DaggerNetworkComponent.create())
            .build()

    }

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun networkComponent(networkComponent: NetworkComponent): Builder

        fun build(): CacheComponent
    }


    fun getCache(): CacheRepository

}