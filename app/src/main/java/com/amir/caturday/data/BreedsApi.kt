package com.amir.caturday.data

import com.amir.caturday.data.dto.BreedDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedsApi {
    @GET("breeds")
    fun getBreeds(): Call<List<BreedDto>>

    @GET("breeds/search")
    fun searchBreeds(
        @Query("q") query: String,
    ): Call<List<BreedDto>>
}
