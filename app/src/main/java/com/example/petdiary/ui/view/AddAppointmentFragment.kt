package com.example.petdiary.ui.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentAddAppointmentBinding
import com.example.petdiary.domain.model.Appointment
import com.example.petdiary.ui.viewmodel.ScheduleViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class AddAppointmentFragment : Fragment() {

    private var _binding: FragmentAddAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var scheduleViewModel: ScheduleViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleViewModel = ViewModelProvider(requireActivity())[ScheduleViewModel::class.java]

        binding.startAppointmentDate.setOnClickListener { showDatePicker { date -> setStartDate(date) } }
        binding.startAppointmentTime.setOnClickListener { showTimePicker { time -> setStartTime(time) } }
        binding.endAppointmentDate.setOnClickListener { showDatePicker { date -> setEndDate(date) } }
        binding.endAppointmentTime.setOnClickListener { showTimePicker { time -> setEndTime(time) } }

        binding.btnCancelAppointment.setOnClickListener {findNavController().navigateUp() }
        binding.btnSaveAppointment.setOnClickListener { saveAppointment() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDatePicker(callback: (LocalDate) -> Unit) {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.addOnPositiveButtonClickListener {
            val localDate = Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
            callback(localDate)
        }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun showTimePicker(callback: (LocalTime) -> Unit) {
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
        timePicker.addOnPositiveButtonClickListener {
            val localTime = LocalTime.of(timePicker.hour, timePicker.minute)
            callback(localTime)
        }
        timePicker.show(parentFragmentManager, "timePicker")
    }

    private fun setStartDate(date: LocalDate) {
        scheduleViewModel.setStartDate(date)
        binding.startAppointmentDate.setText(
            scheduleViewModel.startDateTime?.toLocalDate().toString()
        )
    }

    private fun setStartTime(time: LocalTime) {
        scheduleViewModel.setStartTime(time)
        binding.startAppointmentTime.setText(
            scheduleViewModel.startDateTime?.toLocalTime().toString()
        )
    }

    private fun setEndDate(date: LocalDate) {
        scheduleViewModel.setEndDate(date)
        binding.endAppointmentDate.setText(
            scheduleViewModel.endDateTime?.toLocalDate().toString()
        )
    }

    private fun setEndTime(time: LocalTime) {
        scheduleViewModel.setEndTime(time)
        binding.endAppointmentTime.setText(
            scheduleViewModel.endDateTime?.toLocalTime().toString()
        )
    }
    private fun saveAppointment() {
        val title = binding.etAppointmentTitle.text.toString()
        val description = binding.etAppointmentDescription.text.toString()
        
        if(title.isEmpty() || scheduleViewModel.startDateTime == null || scheduleViewModel.endDateTime == null) {
            Toast.makeText(requireContext(), "Fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        scheduleViewModel.saveAppointment(requireContext(), title, description)
        findNavController().navigateUp()
    }
}
