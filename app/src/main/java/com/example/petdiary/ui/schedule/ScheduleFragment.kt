package com.example.petdiary.ui.schedule

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdiary.R
import com.example.petdiary.databinding.FragmentScheduleBinding
import com.example.petdiary.ui.appointment.Appointment
import com.example.petdiary.ui.appointment.AppointmentAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null

    private val binding get() = _binding!!

    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleViewModel = ViewModelProvider(requireActivity())[ScheduleViewModel::class.java]

        val recyclerView = binding.appointmentList
        recyclerView.layoutManager = LinearLayoutManager(context)
        appointmentAdapter = AppointmentAdapter(scheduleViewModel.appointments.value ?: emptyList()) { appointment ->
            showAppointmentDetails(appointment)
        }
        recyclerView.adapter = appointmentAdapter

        scheduleViewModel.appointments.observe(viewLifecycleOwner) {
            appointmentAdapter.notifyDataSetChanged()
        }

        binding.fabAddAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_addAppointmentFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAppointmentDetails(appointment: Appointment) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(appointment.title)
            .setMessage("Date: ${appointment.date}\nTime: ${appointment.time}\n\nDescription: ${appointment.description}")
            .setPositiveButton("OK", null)
            .show()
    }
}