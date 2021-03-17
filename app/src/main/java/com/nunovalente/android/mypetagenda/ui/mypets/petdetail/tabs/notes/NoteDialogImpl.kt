package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.databinding.DialogAddNoteBinding
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.ui.common.dialog.BaseDialog
import com.nunovalente.android.mypetagenda.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteDialogImpl @Inject constructor(
    private val repository: NoteRepository,
    private val activity: AppCompatActivity,
    private val inflater: LayoutInflater,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : BaseDialog() {

    private lateinit var binding: DialogAddNoteBinding
    private var noteRetrieved: Note? = null
    private var petId: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        injector.inject(this)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_note, null, false)
        val builder = AlertDialog.Builder(activity)
        builder.setView(binding.root)
            .setTitle(R.string.add_note)

        if(arguments != null) {
            noteRetrieved = arguments?.getParcelable(Constants.NOTE)
            petId = arguments?.getInt(Constants.PET_ID)!!
            binding.editDialogNote.setText(noteRetrieved?.text)
            if(noteRetrieved != null) {
                binding.tvAddNote.text = getString(R.string.update_note)
            }
        }

        setListeners()
        return builder.create()
    }

    private fun setListeners() {
        binding.tvAddNote.setOnClickListener {
            coroutineScope.launch {
                validateNote()
            }
        }

        binding.tvNoteCancel.setOnClickListener {
            this.dismiss()
        }
    }

    private suspend fun validateNote() {
        val noteText: String = binding.editDialogNote.text.toString()
        val note = Note(petId, noteText)
        if (noteText.isNotEmpty() && noteRetrieved == null) {
            repository.addNote(note)
            this.dismiss()
        } else if (noteText.isNotEmpty() && noteRetrieved != null) {
            noteRetrieved!!.text = noteText
            repository.updateNote(noteRetrieved!!)
            this.dismiss()
        } else {
            binding.tvNoteError.visibility = View.VISIBLE
        }
    }
}