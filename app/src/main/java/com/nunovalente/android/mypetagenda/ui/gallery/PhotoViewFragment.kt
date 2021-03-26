package com.nunovalente.android.mypetagenda.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentPhotoViewBinding
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.util.GlideUtil.load

class PhotoViewFragment : BaseFragment() {

    private lateinit var binding: FragmentPhotoViewBinding

    private val args: PhotoViewFragmentArgs by navArgs()
    private val viewModel: PhotoViewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_photo_view, container, false)

        binding.photoPreviewBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()

        val image = args.image

        binding.imageContainer.load(image) {
            startPostponedEnterTransition()
        }

       sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }
}