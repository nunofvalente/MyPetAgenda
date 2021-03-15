package com.nunovalente.android.mypetagenda.util

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.data.NoteRepository
import com.nunovalente.android.mypetagenda.databinding.DialogAddNoteBinding
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.ui.common.dialog.BaseDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteDialogImpl @Inject constructor(
    private val repository: NoteRepository,
    private val activity: AppCompatActivity,
    private val inflater: LayoutInflater,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) : BaseDialog() {

    private lateinit var binding: DialogAddNoteBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        injector.inject(this)
        val builder = AlertDialog.Builder(activity)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_note, null, false)
        builder.setView(binding.root)
            .setTitle(R.string.add_note)

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
        val note = Note(noteText)
        if (noteText.isNotEmpty()) {
            repository.addNote(note)
            this.dismiss()
        } else {
            binding.tvNoteError.visibility = View.VISIBLE
        }
    }
}