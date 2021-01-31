package io.budge.goobois.data.api

import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.GENERIC_ERROR_CODE
import io.budge.goobois.utils.GENERIC_ERROR_MESSAGE
import io.budge.goobois.utils.Result
import io.budge.goobois.utils.getAPIResult
import javax.inject.Inject

class DogsRepository @Inject constructor(private val apiService: DogsApiService) {

    suspend fun getBreeds(): Result<MutableList<DogBreed>>{
        return try {
            getAPIResult(apiService.getDogBreeds())
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, e.localizedMessage?: GENERIC_ERROR_MESSAGE)
        }
    }

    suspend fun getDogs(breedId: Int): Result<MutableList<Dog>> {
        return try {
            getAPIResult(apiService.getDogs(100, breedId))
        } catch (e: Exception) {
            Result.Error(GENERIC_ERROR_CODE, e.localizedMessage?: GENERIC_ERROR_MESSAGE)
        }
    }
}