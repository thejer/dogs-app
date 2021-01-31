package io.budge.goobois.data.api

import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsApiService {

    @GET("/v1/breeds")
    suspend fun getDogBreeds(): Response<MutableList<DogBreed>>

    @GET("/v1/images/search")
    suspend fun getDogs(
        @Query("limit") limit: Int,
        @Query("breed_id") breedId: Int,
    ): Response<MutableList<Dog>>

}