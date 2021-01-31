package io.budge.goobois.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import io.budge.goobois.App
import io.budge.goobois.R
import io.budge.goobois.data.model.DogBreed
import io.budge.goobois.databinding.ActivityMainBinding
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as App).component.inject(this)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.getDogBreeds()
        val dogsAdapter = DogsListAdapter()

        binding.dogsRecyclerview.adapter = dogsAdapter

        mainViewModel.dogBreeds.observe(this, {
            it?.let {
                setUpBreedsSpinner(it)
            }
        })

        mainViewModel.dogs.observe(this, {
            it?.let {
                dogsAdapter.submitList(it)
            }
        })

        mainViewModel.errorMessage.observe(this, {
            it?.let {
                showSnackbar(it, binding.root)
            }
        })
    }

    private fun setUpBreedsSpinner(dogBreeds: MutableList<DogBreed>) {
        val breedNames = dogBreeds.map { it.name }
        val spinnerAdapter: ArrayAdapter<*> =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, breedNames
            )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.selectBreedSpinner.adapter = spinnerAdapter
        binding.selectBreedSpinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                mainViewModel.getDogByBreed(dogBreeds[position].id)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun showSnackbar(snackbarText: String, view: View) {
        Snackbar.make(view, snackbarText, Snackbar.LENGTH_SHORT).run {
            show()
        }
    }

}