package com.nunovalente.android.mypetagenda.ui.mypets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.AdapterMyPetsBinding
import com.nunovalente.android.mypetagenda.models.Pet


class MyPetsAdapter(private val clickListener: PetClickListener) : ListAdapter<Pet, MyPetsAdapter.MyPetsViewHolder>(MyPetsDiffCallback()) {

    class MyPetsViewHolder private constructor(private val binding: AdapterMyPetsBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: Pet) {
            binding.pet = pet
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyPetsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AdapterMyPetsBinding.inflate(layoutInflater, parent, false)
                return MyPetsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPetsViewHolder {
        return MyPetsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyPetsViewHolder, position: Int) {
        val pet = getItem(position)
        val imageView: ImageView = holder.itemView.findViewById(R.id.pet_image_adapter)

        holder.bind(pet)
        holder.itemView.setOnClickListener {
            ViewCompat.setTransitionName(imageView, "image_${pet.name}")
            clickListener.onClick(imageView, pet)
        }
    }
}

class MyPetsDiffCallback: DiffUtil.ItemCallback<Pet>() {
    override fun areItemsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pet, newItem: Pet): Boolean {
        return oldItem == newItem
    }
}

class PetClickListener(private val clickListener: (transitionView: ImageView, pet: Pet) -> Unit) {
    fun onClick(transitionView: ImageView, pet: Pet) = clickListener(transitionView, pet)
}