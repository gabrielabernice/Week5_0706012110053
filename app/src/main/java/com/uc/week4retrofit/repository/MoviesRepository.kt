package com.uc.week4retrofit.repository

import com.uc.week4retrofit.retrofit.EndPointApi
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: EndPointApi) {



    suspend fun getNowPlayingResults(apiKey: String, language: String, page: Int) = api.getNowPlaying(apiKey, language, page)

    suspend fun getMovieDetailResults(apiKey: String, movieId: Int) = api.getMovieDetails(movieId, apiKey)


}