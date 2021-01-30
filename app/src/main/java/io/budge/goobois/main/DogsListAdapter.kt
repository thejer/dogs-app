package io.budge.goobois.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.budge.goobois.data.model.Dog
import io.budge.goobois.databinding.DogItemLayoutBinding

class DogsListAdapter: ListAdapter<Dog, DogsListAdapter.DogsViewHolder>(DogsListDiffUCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsViewHolder =
        DogsViewHolder.from(parent)

    override fun onBindViewHolder(holder: DogsViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DogsViewHolder private constructor(private val binding: DogItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Dog) {
            binding.dog = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): DogsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = DogItemLayoutBinding.inflate(layoutInflater, parent, false)
                return DogsViewHolder(binding)
            }
        }
    }
}

class DogsListDiffUCallback: DiffUtil.ItemCallback<Dog>() {
    override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem.id == newItem.id

    }

    override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
        return oldItem == newItem
    }
}