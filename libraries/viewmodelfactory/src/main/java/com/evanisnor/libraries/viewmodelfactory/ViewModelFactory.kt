package com.evanisnor.libraries.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evanisnor.moviereminder.cache.Cache
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val cache: Cache
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Cache::class.java).newInstance(cache)
}