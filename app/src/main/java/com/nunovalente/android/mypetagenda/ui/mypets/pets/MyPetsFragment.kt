package com.nunovalente.android.mypetagenda.ui.mypets.pets

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.findNavController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentMypetsBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.MyPetsAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.PetClickListener
import com.nunovalente.android.mypetagenda.ui.mypets.PetOnLongClickListener
import javax.inject.Inject

class MyPetsFragment : BaseFragment() {

    @Inject lateinit var factory: ViewModelFactory

    private lateinit var viewModel: MyPetsViewModel
    private lateinit var binding: FragmentMypetsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return if (::binding.isInitialized) {
            binding.root
        } else {
            injector.inject(this)
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mypets, container, false)

            viewModel = ViewModelProvider(this, factory).get(MyPetsViewModel::class.java)
            binding.viewModel = viewModel
            binding.lifecycleOwner = this

            binding.fabMyPets.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_mypets_to_addPetFragment)
            }

            setRecyclerAdapter()

            viewModel.petList.observe(viewLifecycleOwner, { petList ->
                if (petList!!.isEmpty()) {
                    showNoData()
                } else {
                    showData()
                }
            })

            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun setRecyclerAdapter() {
        binding.recyclerMyPets.apply {
            this.adapter = MyPetsAdapter(

                PetClickListener {transitionView, pet ->
                val extras = FragmentNavigatorExtras(transitionView to pet.name)
                val directions = MyPetsFragmentDirections.actionNavigationMypetsToPetDetailFragment(pet)
                findNavController().navigate(directions, extras)
            },
                PetOnLongClickListener { pet ->
                    val dialog = AlertDialog.Builder(requireActivity())
                        .setTitle(context.getString(R.string.delete))
                        .setMessage(context.getString(R.string.are_you_sure_you_want_to_delete_note))
                        .setPositiveButton(context.getString(R.string.yes)) { dialogInterface: DialogInterface, _: Int ->
                            viewModel.deletePet(pet)
                            dialogInterface.dismiss()
                        }
                        .setNegativeButton(context.getString(R.string.no)) { dialogInterface: DialogInterface, _: Int ->
                            dialogInterface.dismiss()
                        }
                    dialog.create()
                    dialog.show()
                }
            )
        }
    }

    private fun showData() {
        binding.recyclerMyPets.visibility = View.VISIBLE
        binding.imageAddMe.visibility = View.GONE
    }

    private fun showNoData() {
        binding.recyclerMyPets.visibility = View.GONE
        binding.imageAddMe.visibility = View.VISIBLE
    }
}