package com.example.petdiary.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.petdiary.R
import com.example.petdiary.services.calendar.CastDates
import com.example.petdiary.ui.model.DiaryNote

class DiaryAdapter(
    private val context: Context,
    private val sections: Map<Int, List<DiaryNote>>
) : BaseExpandableListAdapter() {

    private val years = sections.keys.toList()

    override fun getGroupCount(): Int {
        return years.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return sections[years[groupPosition]]?.size ?: 0
    }

    override fun getGroup(groupPosition: Int): Any {
        return years[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return sections[years[groupPosition]]?.get(childPosition)!!
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val year = getGroup(groupPosition) as Int
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_year, parent, false)
        val yearTextView: TextView = view.findViewById(R.id.yearTextView)
        yearTextView.text = year.toString()
        return view
    }

    @SuppressLint("SetTextI18n")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val note = getChild(groupPosition, childPosition) as DiaryNote
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_note, parent, false)

        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val petsTextView: TextView = view.findViewById(R.id.petsTextView)
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)

        titleTextView.text = note.title

        val petsNames = if (note.pets.isNotEmpty()) {
            note.pets.joinToString(separator = ", ") { it.name }
        } else {
            "No pets"
        }
        petsTextView.text = petsNames

        val date = CastDates.fromStringToDate(note.date)
        dateTextView.text = "${date.month} - ${date.dayOfMonth}"

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}