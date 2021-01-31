package io.budge.goobois.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.progressindicator.LinearProgressIndicator
import io.budge.goobois.R
import io.budge.goobois.data.model.Dog
import io.budge.goobois.main.DogsListAdapter


@BindingAdapter("isLoading")
fun bindLoadingIndicator(progressIndicator: LinearProgressIndicator, isLoading: Boolean) {
    if (isLoading) progressIndicator.visibility = View.VISIBLE
    else progressIndicator.visibility = View.GONE
}


@BindingAdapter("imageUrl")
fun bindImageView(imageView: ImageView, url: String?) {
    imageView.load(url) {
        placeholder(R.drawable.dog)
        error(R.drawable.dog)
        crossfade(true)
    }
}

@BindingAdapter("dogs")
fun bindDogs(recyclerView: RecyclerView, data: MutableList<Dog>?) {
        val adapter = recyclerView.adapter as DogsListAdapter
        adapter.submitList(data)
}