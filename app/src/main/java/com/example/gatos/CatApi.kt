package com.example.gatos
import com.example.gatos.CatResponse
import retrofit2.http.GET
import retrofit2.Call

interface CatApi {
    @GET("v1/images/search?limit=1")
    fun getRandomCat(): Call<List<CatUrlResponse>>
}

