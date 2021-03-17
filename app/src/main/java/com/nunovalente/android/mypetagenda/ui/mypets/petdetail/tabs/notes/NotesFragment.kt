package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.databinding.FragmentNotesBinding
import com.nunovalente.android.mypetagenda.ui.common.ViewModelFactory
import com.nunovalente.android.mypetagenda.ui.common.fragment.BaseFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailFragment
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailFragmentArgs
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.PetDetailViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class NotesFragment : BaseFragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    @Inject
    lateinit var dialogImpl: NoteDialogImpl

    private lateinit var binding: FragmentNotesBinding
    private lateinit var viewModelDetail: PetDetailViewModel
    private lateinit var viewModel: NotesViewModel

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        injector.inject(this)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false)

        viewModel = ViewModelProvider(this, factory).get(NotesViewModel::class.java)
        viewModelDetail = ViewModelProvider(requireActivity(), factory).get(PetDetailViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.loadNotes(viewModelDetail.getPetRetrieved()!!)

        setRecyclerAdapter()

        return binding.root
    }

    private fun setRecyclerAdapter(): Boolean {
        binding.recyclerNotes.apply {
            this.adapter = MyNotesAdapter(

                NoteClickListener { note ->
                    val args = Bundle()
                    args.putParcelable(context.getString(R.string.note) , note)
                    dialogImpl.arguments = args
                    dialogImpl.show(parentFragmentManager, TAG)
                },

                NoteOnLongClickListener { note ->
                    val dialog = AlertDialog.Builder(requireActivity())
                        .setTitle(context.getString(R.string.delete))
                        .setMessage(context.getString(R.string.are_you_sure_you_want_to_delete_note))
                        .setPositiveButton(context.getString(R.string.yes)) { dialogInterface: DialogInterface, _: Int ->
                            coroutineScope.launch {
                              //  viewModel.deleteNote(note)
                            }
                            dialogInterface.dismiss()
                        }
                        .setNegativeButton(context.getString(R.string.no)) { dialogInterface: DialogInterface, _: Int ->
                            dialogInterface.dismiss()
                        }
                    dialog.create()
                    dialog.show()
                    true
                })

        }
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotesFragment()

        private val TAG: String = PetDetailFragment::class.java.simpleName
    }
}