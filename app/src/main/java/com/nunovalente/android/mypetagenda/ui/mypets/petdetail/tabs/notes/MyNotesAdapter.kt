package com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.nunovalente.android.mypetagenda.databinding.AdapterNotesBinding
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet

class MyNotesAdapter(private val clickListener: NoteClickListener, private val longClickListener: NoteOnLongClickListener): RecyclerView.Adapter<MyNotesAdapter.MyNotesViewHolder>() {

    private var notesList: List<Note> = listOf()

    class MyNotesViewHolder(val binding: AdapterNotesBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyNotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterNotesBinding.inflate(inflater)

        return MyNotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyNotesViewHolder, position: Int) {
        val note = notesList[position]

        holder.binding.note = note
        holder.itemView.setOnClickListener {
            clickListener.onClick(note)
        }
        holder.itemView.setOnLongClickListener {
            longClickListener.onLongClick(note)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun setValue(list: List<Note>) {
        notesList = list
        notifyDataSetChanged()
    }
}

class NoteClickListener(private val clickListener: (note: Note) -> Unit) {
    fun onClick(note: Note) = clickListener(note)
}

class NoteOnLongClickListener(private val onLongClickListener: (note: Note) -> Boolean) {
    fun onLongClick(note: Note) = onLongClickListener(note)
}