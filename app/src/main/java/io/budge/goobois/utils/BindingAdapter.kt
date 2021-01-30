package io.budge.goobois.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.progressindicator.LinearProgressIndicator


@BindingAdapter("isLoading")
fun bindLoadingIndicator(progressIndicator: LinearProgressIndicator, isLoading: Boolean) {
    if (isLoading) progressIndicator.visibility = View.VISIBLE
    else progressIndicator.visibility = View.GONE
}


@BindingAdapter("imageUrl")
fun bindImageView(imageView: ImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
    }
}