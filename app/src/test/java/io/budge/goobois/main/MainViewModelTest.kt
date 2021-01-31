package io.budge.goobois.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.budge.goobois.FakeDogsRepository
import io.budge.goobois.FakeDogsRepository.Companion.ERROR_GETTING_BREEDS
import io.budge.goobois.FakeDogsRepository.Companion.ERROR_GETTING_DOGS
import io.budge.goobois.LiveDataTestUtil
import io.budge.goobois.data.model.Dog
import io.budge.goobois.data.model.DogBreed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: FakeDogsRepository
    private lateinit var viewModel: MainViewModel

    @OptIn(ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp(){
        Dispatchers.setMain(mainThreadSurrogate)
        val dogsData = linkedMapOf(
            1 to mutableListOf(  Dog("ewfs", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("eewfs", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("edwfs", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("ewffs", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg")
            ),
            2 to mutableListOf(
                Dog("wrf", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("wty", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("spl", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
                Dog("wcs", "https://cdn2.thedogapi.com/images/0LJiOVlxp.jpg"),
            )
        )

        val dogBreeds = mutableListOf(
            DogBreed(1, "Afghan Hound"),
            DogBreed(2, "Akita")
        )
        repository = FakeDogsRepository(dogsData, dogBreeds)
        viewModel = MainViewModel(repository)
    }

    @After
    fun tearDown() {
        repository.clearBreeds()
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `getDogBreeds on success dogBreeds count must be 2`() {
        viewModel.getDogBreeds()
        assert(LiveDataTestUtil.getValue(viewModel.dogBreeds).size == 2)
    }

    @Test
    fun `getDogBreeds on failure error message must be valid`() {
        repository.clearBreeds()
        viewModel.getDogBreeds()
        assert(LiveDataTestUtil.getValue(viewModel.errorMessage) == ERROR_GETTING_BREEDS)
    }


    @Test
    fun `getDogsByBreed on success dogs must be as espected`() {
        viewModel.getDogsByBreed(1)
        assert(LiveDataTestUtil.getValue(viewModel.dogs)[0].id == "ewfs")
    }

    @Test
    fun `getDogsByBreed on failure error message must be valid`() {
        repository.clearBreeds()
        viewModel.getDogsByBreed(5)
        assert(LiveDataTestUtil.getValue(viewModel.errorMessage) == ERROR_GETTING_DOGS)
    }


}