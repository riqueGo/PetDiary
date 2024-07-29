package com.example.petdiary.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petdiary.domain.model.Appointment
import com.example.petdiary.services.calendar.CalendarLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleViewModel : ViewModel() {

    private val _appointments = MutableLiveData<MutableList<Appointment>>().apply { value = mutableListOf() }
    val appointments: LiveData<MutableList<Appointment>> = _appointments

    private var _startDateTime: LocalDateTime? = null
    val startDateTime: LocalDateTime?
        get() = _startDateTime

    private var _endDateTime: LocalDateTime? = null
    val endDateTime: LocalDateTime?
        get() = _endDateTime

    private fun addAppointment(appointment: Appointment) {
        _appointments.value?.add(appointment)
        setAppointments(_appointments.value!!)
    }

    fun setAppointments(appointments: MutableList<Appointment>) {
        _appointments.value?.clear()
        _appointments.value?.addAll(appointments)
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
}
