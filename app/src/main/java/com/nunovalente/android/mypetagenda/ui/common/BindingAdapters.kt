package com.nunovalente.android.mypetagenda.ui.common

import android.graphics.Bitmap
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.models.Note
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.models.Reminder
import com.nunovalente.android.mypetagenda.ui.gallery.GalleryAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.MyPetsAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.notes.MyNotesAdapter
import com.nunovalente.android.mypetagenda.ui.mypets.petdetail.tabs.reminders.ReminderAdapter


@BindingAdapter("loadImage")
fun loadImage(imageView: ImageView, url: String?) {
    url?.let {
        GlideApp.with(imageView.context).load(it)
            .placeholder(R.drawable.default_image_pet)
            .error(R.drawable.default_image_pet)
            .circleCrop()
            .into(imageView)
    }
}

@BindingAdapter("submitPetList")
fun loadItems(recyclerView: RecyclerView, petsList: List<Pet>?) {
    petsList?.let { pets ->
        (recyclerView.adapter as MyPetsAdapter).submitList(pets)
    }
}

@BindingAdapter("submitNoteList")
fun loadNotes(recyclerView: RecyclerView, noteList: List<Note>?) {
    noteList?.let { notes ->
        (recyclerView.adapter as MyNotesAdapter).setValue(notes)
    }
}

@BindingAdapter("submitReminderList")
fun loadReminders(recyclerView: RecyclerView, reminderList: List<Reminder>?) {
    reminderList?.let { reminders ->
        (recyclerView.adapter as ReminderAdapter).setValue(reminders)
    }
}

@BindingAdapter("submitGalleryList")
fun loadImages(recyclerView: RecyclerView, imageList: List<Bitmap>?) {
    imageList?.let { images ->
        (recyclerView.adapter as GalleryAdapter).setValue(images)
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T>{
    return adapter as ArrayAdapter<T>
}