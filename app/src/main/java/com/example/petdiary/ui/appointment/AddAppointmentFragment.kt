package com.example.petdiary.ui.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentAddAppointmentBinding
import com.example.petdiary.ui.schedule.ScheduleViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class AddAppointmentFragment : Fragment() {

    private var _binding: FragmentAddAppointmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var scheduleViewModel: ScheduleViewModel
    private var selectedDate: LocalDate? = null
    private var selectedTime: LocalTime? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scheduleViewModel = ViewModelProvider(requireActivity())[ScheduleViewModel::class.java]

        binding.etAppointmentDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.etAppointmentTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.btnCancelAppointment.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveAppointment.setOnClickListener {
            val title = binding.etAppointmentTitle.text.toString()
            val description = binding.etAppointmentDescription.text.toString()
            val alarm = binding.switchAlarm.isChecked

            if (selectedDate == null || selectedTime == null) {
                // Show error to the user
                return@setOnClickListener
            }

            val newAppointment = Appointment(title, selectedDate!!, selectedTime!!, alarm, description)
            scheduleViewModel.addAppointment(newAppointment)
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
            binding.etAppointmentDate.setText(selectedDate.toString())
        }, year, month, day)
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            selectedTime = LocalTime.of(selectedHour, selectedMinute)
            binding.etAppointmentTime.setText(selectedTime.toString())
        }, hour, minute, true)
        timePickerDialog.show()
    }
}
