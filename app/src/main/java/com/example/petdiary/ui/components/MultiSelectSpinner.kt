package com.example.petdiary.ui.components

import android.R
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.SpinnerAdapter

class MultiSelectSpinner(context: Context, attrs: AttributeSet) : Spinner(context, attrs),
    DialogInterface.OnMultiChoiceClickListener {

    private var items: Array<String>? = null
    private var selected: BooleanArray? = null

    var checkedItems: List<String> = emptyList()
        get() = items?.filterIndexed { index, _ -> selected!![index] } ?: emptyList()
        private set

    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
        selected!![which] = isChecked
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun performClick(): Boolean {
        AlertDialog.Builder(context)
            .setMultiChoiceItems(items, selected, this)
            .setPositiveButton("OK") { _, _ -> }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
        return true
    }

    fun setItems(items: Array<String>) {
        this.items = items
        selected = BooleanArray(items.size)
        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, arrayOf("Select Pets"))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setAdapter(adapter)
    }

    override fun setAdapter(adapter: SpinnerAdapter?) {
        super.setAdapter(adapter)
    }
}