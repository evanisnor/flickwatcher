package com.evanisnor.moviereminder

import com.evanisnor.moviereminder.cache.CacheComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        CacheComponent::class
    ]
)
interface MainComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(movieReminderApp: MovieReminderApp)

}