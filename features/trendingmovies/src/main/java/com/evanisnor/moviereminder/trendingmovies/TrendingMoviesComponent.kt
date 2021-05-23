package com.evanisnor.moviereminder.trendingmovies

import dagger.Subcomponent

@TrendingMoviesScope
@Subcomponent
interface TrendingMoviesComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): TrendingMoviesComponent
    }

}