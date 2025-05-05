package com.amir.caturday.data.remote

import com.amir.caturday.data.remote.dto.BreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedsApi {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): List<BreedDto>

    @GET("breeds/search")
    suspend fun searchBreeds(
        @Query("q") query: String,
    ): List<BreedDto>
}
