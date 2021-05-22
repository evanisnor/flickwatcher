package com.evanisnor.moviereminder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.evanisnor.moviereminder.cache.Cache
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var cache: Cache

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        runBlocking {
            launch {
                cache.fetchTrendingMovies()
            }
            launch {
                cache.receiveTrendingMovies().collect {
                    for (movie in it) {
                        Log.i("MovieReminderApp", movie.title)
                    }
                }
            }

        }
    }
}