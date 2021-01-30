package io.budge.goobois.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.budge.goobois.data.api.DogsRepository
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.utils.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val dogsRepository: DogsRepository
): ViewModel() {

    private val _dogBreeds = MutableLiveData<MutableList<DogBreed>>()
    val dogBreeds: LiveData<MutableList<DogBreed>>
        get() = _dogBreeds

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    fun getDogBreeds(){
        _isLoading.value = true
        viewModelScope.launch {
            when (val result = dogsRepository.getBreeds()) {
                is Result.Success -> {
                    _isLoading.value = false
                    _dogBreeds.value = result.data
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.errorMessage
                }
            }
        }
    }

}