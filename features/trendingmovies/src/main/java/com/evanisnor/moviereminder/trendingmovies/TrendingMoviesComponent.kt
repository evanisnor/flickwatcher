package com.evanisnor.moviereminder.trendingmovies

import dagger.Subcomponent

@Subcomponent
interface TrendingMoviesComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): TrendingMoviesComponent
    }

    fun inject(trendingMoviesViewModel: TrendingMoviesViewModel)

}