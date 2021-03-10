package com.nunovalente.android.mypetagenda.ui.common.util

import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nunovalente.android.mypetagenda.models.Pet
import com.nunovalente.android.mypetagenda.ui.mypets.MyPetsAdapter


@BindingAdapter("submitPetList")
fun loadItems(recyclerView: RecyclerView, petsList: List<Pet>?) {
    petsList?.let { pets ->
        (recyclerView.adapter as MyPetsAdapter).submitList(pets)
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