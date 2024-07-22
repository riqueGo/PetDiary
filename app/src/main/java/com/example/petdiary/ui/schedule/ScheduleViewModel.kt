package com.example.petdiary.ui.schedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petdiary.ui.appointment.Appointment
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel : ViewModel() {

    private val _appointments = MutableLiveData<MutableList<Appointment>>().apply {
        value = mutableListOf(
            Appointment("Meeting with John", LocalDate.of(2024, 7, 20), LocalTime.of(14, 0), false,"Discuss project details"),
            Appointment("Doctor Appointment", LocalDate.of(2024, 7, 22), LocalTime.of(10, 30), false,"Annual check-up")
        )
    }
    val appointments: LiveData<MutableList<Appointment>> = _appointments

    fun addAppointment(appointment: Appointment) {
        _appointments.value?.add(appointment)
        _appointments.value = _appointments.value // Trigger LiveData observer
    }
}