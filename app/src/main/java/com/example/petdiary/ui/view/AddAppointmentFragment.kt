package com.example.petdiary.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentAddAppointmentBinding
import com.example.petdiary.ui.components.CalendarPicker
import com.example.petdiary.ui.viewmodel.ScheduleViewModel
import java.time.LocalDate
import java.time.LocalTime

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

        binding.startAppointmentDate.setOnClickListener { CalendarPicker.showDatePicker(parentFragmentManager) { date -> setStartDate(date) } }
        binding.startAppointmentTime.setOnClickListener { CalendarPicker.showTimePicker(parentFragmentManager) { time -> setStartTime(time) } }
        binding.endAppointmentDate.setOnClickListener { CalendarPicker.showDatePicker(parentFragmentManager) { date -> setEndDate(date) } }
        binding.endAppointmentTime.setOnClickListener { CalendarPicker.showTimePicker(parentFragmentManager) { time -> setEndTime(time) } }

        binding.btnCancelAppointment.setOnClickListener {findNavController().navigateUp() }
        binding.btnSaveAppointment.setOnClickListener { saveAppointment() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
