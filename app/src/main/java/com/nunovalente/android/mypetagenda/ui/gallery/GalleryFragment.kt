package com.nunovalente.android.mypetagenda.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentGalleryBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import javax.inject.Inject

class GalleryFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: FragmentGalleryBinding
    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        return if (::binding.isInitialized) {
            binding.root
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gallery, container, false)

            galleryViewModel = ViewModelProvider(this, factory).get(GalleryViewModel::class.java)
            binding.viewModel = galleryViewModel
            binding.lifecycleOwner = this

            binding.fabCamera.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_gallery_to_cameraFragment)
            }

            galleryViewModel.images.observe(requireActivity(), { images ->
                if (images.isNullOrEmpty()) {
                    showNoImages()
                } else {
                    showImages()
                }
            })

            setRecyclerAdapter()

            //TODO Rotate portrait pictures in recycler view


            return binding.root
        }
    }



    private fun setRecyclerAdapter() {
        binding.recyclerImages.apply {
            this.adapter = GalleryAdapter(GalleryClickListener { transitionView, image ->
                val extras =
                    FragmentNavigatorExtras(transitionView to getString(R.string.image_container))
                val directions =
                    GalleryFragmentDirections.actionNavigationGalleryToPhotoViewFragment(image)
                findNavController().navigate(directions, extras)
            })
        }
    }

    private fun showNoImages() {
        binding.galleryImagePhotoMe.visibility = View.VISIBLE
        binding.recyclerImages.visibility = View.GONE
    }

    private fun showImages() {
        binding.galleryImagePhotoMe.visibility = View.GONE
        binding.recyclerImages.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        galleryViewModel.refresh()
    }
}