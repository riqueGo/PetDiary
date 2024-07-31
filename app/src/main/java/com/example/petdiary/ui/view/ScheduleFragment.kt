package com.example.petdiary.ui.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petdiary.R
import com.example.petdiary.databinding.FragmentScheduleBinding
import com.example.petdiary.domain.model.Appointment
import com.example.petdiary.ui.adapters.AppointmentAdapter
import com.example.petdiary.ui.viewmodel.ScheduleViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.time.LocalDateTime
import java.time.ZoneOffset
@RequiresApi(Build.VERSION_CODES.O)
class ScheduleFragment : Fragment() {

    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var appointmentAdapter: AppointmentAdapter

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.READ_CALENDAR] == true &&
            permissions[Manifest.permission.WRITE_CALENDAR] == true
        ) {
            ensurePetDiaryCalendarExists()
        } else {
            // Show a message to the user explaining why the permissions are needed
        }
    }

    private fun checkCalendarPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.WRITE_CALENDAR
                    ) == PackageManager.PERMISSION_GRANTED -> {
                ensurePetDiaryCalendarExists()
            }

            else -> {
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR)
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
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

        checkCalendarPermissions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun ensurePetDiaryCalendarExists() {
        val calendarId = getPetDiaryCalendarId()
        if (calendarId == -1L) {
            createPetDiaryCalendar()
            val newCalendarId = getPetDiaryCalendarId()
            if (newCalendarId != -1L) {
                getCalendarEvents(newCalendarId)
            }
        } else {
            getCalendarEvents(calendarId)
        }
    }

    @SuppressLint("Range")
    private fun getPetDiaryCalendarId(): Long {
        val cursor: Cursor? = requireContext().contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            arrayOf(CalendarContract.Calendars._ID),
            "${CalendarContract.Calendars.NAME} = ?",
            arrayOf("PetDiary"),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getLong(it.getColumnIndex(CalendarContract.Calendars._ID))
            }
        }
        return -1L
    }

    private fun createPetDiaryCalendar() {
        val contentValues = ContentValues().apply {
            put(CalendarContract.Calendars.ACCOUNT_NAME, "henrique.oliveira.gomes@gmail.com")
            put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.google")
            put(CalendarContract.Calendars.NAME, "PetDiary")
            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "PetDiary")
            put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER)
            put(CalendarContract.Calendars.OWNER_ACCOUNT, "henrique.oliveira.gomes@gmail.com")
            put(CalendarContract.Calendars.VISIBLE, 1)
            put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        }

        val uri = CalendarContract.Calendars.CONTENT_URI.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, "henrique.oliveira.gomes@gmail.com")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.google")
            .build()

        requireContext().contentResolver.insert(uri, contentValues)
    }

    private fun showAppointmentDetails(appointment: Appointment) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(appointment.title)
            .setMessage("Start: ${appointment.startDateTime}\nEnd: ${appointment.endDateTime}\n\nDescription: ${appointment.description}")
            .setPositiveButton("OK", null)
            .show()
    }

    @SuppressLint("Range")
    private fun getCalendarEvents(calendarId: Long) {
        AsyncTask.execute {
            val cursor: Cursor? = requireContext().contentResolver.query(
                CalendarContract.Events.CONTENT_URI,
                arrayOf(
                    CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DTSTART,
                    CalendarContract.Events.DTEND,
                    CalendarContract.Events.DESCRIPTION
                ),
                "${CalendarContract.Events.CALENDAR_ID} = ?",
                arrayOf(calendarId.toString()),
                CalendarContract.Events.DTSTART + " ASC"
            )

            val events = mutableListOf<Appointment>()
            cursor?.use {
                while (it.moveToNext()) {
                    val id = it.getLong(it.getColumnIndex(CalendarContract.Events._ID))
                    val title = it.getString(it.getColumnIndex(CalendarContract.Events.TITLE))
                    val startDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTSTART))
                    val endDate = it.getLong(it.getColumnIndex(CalendarContract.Events.DTEND))
                    val description = it.getString(it.getColumnIndex(CalendarContract.Events.DESCRIPTION))

                    val startDateTime = LocalDateTime.ofEpochSecond(startDate / 1000, 0, ZoneOffset.UTC)
                    val endDateTime = LocalDateTime.ofEpochSecond(endDate / 1000, 0, ZoneOffset.UTC)

                    events.add(
                        Appointment(
                            id = id,
                            title = title,
                            startDateTime = startDateTime,
                            endDateTime = endDateTime,
                            description = description
                        )
                    )
                }
            }
            scheduleViewModel.setAppointments(events)
        }
    }
}