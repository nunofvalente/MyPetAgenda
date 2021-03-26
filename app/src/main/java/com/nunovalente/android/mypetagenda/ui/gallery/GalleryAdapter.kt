package com.nunovalente.android.mypetagenda.ui.gallery

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.AdapterGalleryBinding
import com.nunovalente.android.mypetagenda.util.GlideUtil.load

class GalleryAdapter(private val fragment: GalleryFragment, private val clickListener: GalleryClickListener) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var imagesList: List<Bitmap> = listOf()

    class GalleryViewHolder(val binding: AdapterGalleryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterGalleryBinding.inflate(inflater)

        return GalleryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val image = imagesList[position]
        val imageView = holder.binding.imageFile

        fragment.postponeEnterTransition()
        imageView.load(image) {
            fragment.startPostponedEnterTransition()
        }

        holder.itemView.setOnClickListener {
            ViewCompat.setTransitionName(imageView, "image_${image.generationId}")
            clickListener.onClick(imageView, image)

        }


        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    fun setValue(list: List<Bitmap>) {
        imagesList = list
        notifyDataSetChanged()
    }
}

class GalleryClickListener(private val clickListener: (transitionView: ImageView, image: Bitmap) -> Unit) {
    fun onClick(transitionView: ImageView, image: Bitmap) = clickListener(transitionView, image)
}