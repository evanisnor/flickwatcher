package com.evanisnor.moviereminder

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface MainComponent {

    fun inject(movieReminderApp: MovieReminderApp)

}