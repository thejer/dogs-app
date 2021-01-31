package io.budge.goobois

import io.budge.goobois.data.api.IDogsRepository
import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.Result

class FakeDogsRepository(
    private val dogsData: LinkedHashMap<Int, MutableList<Dog>>,
    private val dogBreeds: MutableList<DogBreed>
): IDogsRepository {

    override suspend fun getBreeds(): Result<MutableList<DogBreed>> {
        if (dogBreeds.isEmpty()) {
            return Result.Error(ERROR_CODE, ERROR_GETTING_BREEDS)
        }
        return Result.Success(dogBreeds)
    }

    override suspend fun getDogs(breedId: Int): Result<MutableList<Dog>> {
        val dogs = dogsData[breedId]
        dogs?.let {
            return Result.Success(dogs)
        }
        return Result.Error(ERROR_CODE, ERROR_GETTING_DOGS)
    }

    fun clearBreeds() {
        dogBreeds.clear()
    }

    companion object {
        const val ERROR_GETTING_BREEDS = "Error getting breeds"
        const val ERROR_GETTING_DOGS = "Error getting dogs"
        const val ERROR_CODE = "0"
    }
}