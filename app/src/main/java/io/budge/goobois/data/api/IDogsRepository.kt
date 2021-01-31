package io.budge.goobois.data.api

import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.Result

interface IDogsRepository {
    suspend fun getBreeds(): Result<MutableList<DogBreed>>
    suspend fun getDogs(breedId: Int): Result<MutableList<Dog>>
}