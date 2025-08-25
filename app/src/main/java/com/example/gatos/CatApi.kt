package com.example.gatos

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApi {
    @GET("images/search")
    suspend fun getRandomCat(): List<CatResponse>

    @GET("images/search")
    suspend fun getCatByBreed(
        @Query("breed_id") breedId: String,
        @Query("api_key") apiKey: String
    ): List<CatResponse>
}
