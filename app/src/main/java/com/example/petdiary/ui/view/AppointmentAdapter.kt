package com.example.petdiary.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petdiary.R
import com.example.petdiary.domain.model.Appointment

class AppointmentAdapter(
    private val appointments: List<Appointment>,
    private val onItemClick: (Appointment) -> Unit
) : RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    inner class AppointmentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.tv_appointment_title)
        val dateTextView: TextView = view.findViewById(R.id.tv_appointment_date)

        fun bind(appointment: Appointment) {
            titleTextView.text = appointment.title
            dateTextView.text = appointment.startDateTime.toString()
            itemView.setOnClickListener {
                onItemClick(appointment)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(appointments[position])
    }

    override fun getItemCount() = appointments.size
}
