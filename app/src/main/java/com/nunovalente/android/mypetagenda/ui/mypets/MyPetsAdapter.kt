package com.nunovalente.android.mypetagenda.ui.mypets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

        holder.bind(pet)
        holder.itemView.setOnClickListener {
            clickListener.onClick(pet)
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

class PetClickListener(private val clickListener: (pet: Pet) -> Unit) {
    fun onClick(pet: Pet) = clickListener(pet)
}