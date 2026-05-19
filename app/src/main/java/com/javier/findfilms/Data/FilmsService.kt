package com.javier.findfilms.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsService {

    @GET("/")
    suspend fun searchFilms(
        @Query("s") query: String,
        @Query("apikey") apiKey: String
    ) : FilmsResponse

    @GET("/")
    suspend fun getFilmById(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String
    ) : Film

    companion object {
        fun getInstance() : FilmsService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(FilmsService::class.java)
        }
    }
}