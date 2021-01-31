package io.budge.goobois.data.api

import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.GENERIC_ERROR_CODE
import io.budge.goobois.utils.GENERIC_ERROR_MESSAGE
import io.budge.goobois.utils.Result
import io.budge.goobois.utils.getAPIResult
import javax.inject.Inject

class DogsRepository @Inject constructor(private val apiService: DogsApiService) : IDogsRepository {

    override suspend fun getBreeds(): Result<MutableList<DogBreed>> {
        return try {
            getAPIResult(apiService.getDogBreeds())
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }

    override suspend fun getDogs(breedId: Int): Result<MutableList<Dog>> {
        return try {
            // Using the specified limit, 102, makes the API return just one dog image,
            // After investigation I found out that setting the limit as 100 gives a good amount of dog images
            getAPIResult(apiService.getDogs(100, breedId))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, GENERIC_ERROR_MESSAGE)
        }
    }
}