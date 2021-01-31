package io.budge.goobois.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.budge.goobois.R
import io.budge.goobois.data.model.Dog
import io.budge.goobois.databinding.DogItemLayoutBinding

class DogsListAdapter: ListAdapter<Dog, DogsListAdapter.DogsViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder =
        DogsViewHolder(DogItemLayoutBinding.bind(parent.inflate(R.layout.dog_item_layout)))

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object  DiffCallback : DiffUtil.ItemCallback<Dog>() {

        override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean =
            oldItem == newItem
    }

    inner class DogsViewHolder (private val binding: DogItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Dog) {
            binding.dog = item
            binding.executePendingBindings()
        }
    }
}

inline fun <reified T> ViewGroup.inflate(@LayoutRes layoutRes: Int): T {
    return LayoutInflater.from(context).inflate(layoutRes, this, false) as T
}
