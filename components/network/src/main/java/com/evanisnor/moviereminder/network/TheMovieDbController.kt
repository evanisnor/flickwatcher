package com.evanisnor.moviereminder.network

import com.evanisnor.moviereminder.model.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@NetworkScope
class TheMovieDbController @Inject constructor(
    private val theMovieDbService: TheMovieDbService
) {

    fun getTrendingMovies(
        onSuccess: (Results?) -> Unit,
        onFailure: (Throwable) -> Unit
    ) {
        theMovieDbService.getTrendingMoviesToday()
            .enqueue(object : Callback<Results> {
                override fun onResponse(
                    call: Call<Results>,
                    response: Response<Results>
                ) {
                    onSuccess(response.body())
                }

                override fun onFailure(
                    call: Call<Results>,
                    t: Throwable
                ) {
                    onFailure(t)
                }

            })
    }
}
