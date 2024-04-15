package com.example.andrbestpractices.domain

import com.example.andrbestpractices.model.StarWarsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StartWarApiService {

    @GET("people/")
    suspend fun getStarWarCharacterResponse(@Query("page") pageNumber: Int): Response<StarWarsResponse>

}