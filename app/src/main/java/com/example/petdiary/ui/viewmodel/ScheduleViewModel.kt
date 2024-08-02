package com.example.petdiary.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petdiary.ui.model.Appointment
import com.example.petdiary.services.calendar.CalendarLoader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScheduleViewModel : ViewModel() {

    private val _appointments = MutableLiveData<MutableList<Appointment>>().apply { value = mutableListOf() }
    val appointments: LiveData<MutableList<Appointment>> = _appointments

    private var _startDateTime: LocalDateTime? = null
    val startDateTime: LocalDateTime?
        get() = _startDateTime

    private var _endDateTime: LocalDateTime? = null
    val endDateTime: LocalDateTime?
        get() = _endDateTime

    fun setAppointments(appointments: MutableList<Appointment>) {
        _appointments.value = appointments
    }

    fun setStartDate(date: LocalDate) {
        _startDateTime = _startDateTime?.with(date) ?: LocalDateTime.of(date, LocalTime.MIDNIGHT)
    }

    fun setStartTime(time: LocalTime) {
        _startDateTime = _startDateTime?.with(time) ?: LocalDateTime.of(LocalDate.now(), time)
    }

    fun setEndDate(date: LocalDate) {
        _endDateTime = _endDateTime?.with(date) ?: LocalDateTime.of(date, LocalTime.MIDNIGHT)
    }

    fun setEndTime(time: LocalTime) {
        _endDateTime = _endDateTime?.with(time) ?: LocalDateTime.of(LocalDate.now(), time)
    }

    fun saveAppointment(context: Context, title: String, description: String) {
        val newAppointment = Appointment(
            title = title,
            startDateTime = _startDateTime!!,
            endDateTime = _endDateTime!!,
            description = description
        )
        addAppointment(newAppointment)
        CalendarLoader(context, "PetDiary", "").addEventToCalendar(newAppointment)
    }

    private fun addAppointment(appointment: Appointment) {
        val updatedAppointments = _appointments.value?.toMutableList() ?: mutableListOf()
        updatedAppointments.add(appointment)
        setAppointments(updatedAppointments)
    }

    fun removeAppointment(appointment: Appointment) {
        val updatedAppointments = _appointments.value?.toMutableList() ?: mutableListOf()
        updatedAppointments.remove(appointment)
        setAppointments(updatedAppointments)
    }
}
