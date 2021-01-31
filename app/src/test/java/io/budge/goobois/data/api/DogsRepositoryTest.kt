package io.budge.goobois.data.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import retrofit2.Response

@RunWith(JUnit4::class)
class DogsRepositoryTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: DogsRepository
    private val mockService = Mockito.mock(DogsApiService::class.java)
    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun before() {
        Dispatchers.setMain(mainThreadSurrogate)
        repository = DogsRepository(mockService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `testGetBreeds onSuccess returnDogBreeds`()  {
        val dogBreeds = mutableListOf<DogBreed>()
        val result = Result.Success(dogBreeds)
        val response = Response.success(dogBreeds)
        runBlocking {
            launch(Dispatchers.Main) {
                `when`(mockService.getDogBreeds()).thenReturn(response)
                assert(result == repository.getBreeds())
            }
        }
    }

    @Test
    fun `testGetBreeds onFailure returnError`()  {
        val result = Result.Error("-1",
            "An error occurred, Please try again")
        runBlocking {
            launch(Dispatchers.Main) {
                `when`(mockService.getDogBreeds()).thenReturn(null)
                assert(result == repository.getBreeds())
            }
        }
    }
    @Test
    fun `testGetDogs onSuccess returnDogs`()  {
        val dogs = mutableListOf<Dog>()
        val result = Result.Success(dogs)
        val response = Response.success(dogs)
        runBlocking {
            launch(Dispatchers.Main) {
                `when`(mockService.getDogs(100, 1)).thenReturn(response)
                assert(result == repository.getDogs(1))
            }
        }
    }

    @Test
    fun `testGetDogs onFailure returnError`()  {
        val result = Result.Error("-1",
            "An error occurred, Please try again")
        runBlocking {
            launch(Dispatchers.Main) {
                `when`(mockService.getDogs(200, 1)).thenReturn(null)
                assert(result == repository.getDogs(1))
            }
        }
    }
}