package io.budge.goobois.main

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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

        mainViewModel.dogBreeds.observe(this, {
            it?.let {
                setUpBreedsSpinner(it)
            }
        })
        val dogsAdapter = DogsListAdapter()
        val manager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        binding.dogsRecyclerview.apply {
            adapter = dogsAdapter
            layoutManager = manager
        }
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
                val dogBreed = parent.selectedItem

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}