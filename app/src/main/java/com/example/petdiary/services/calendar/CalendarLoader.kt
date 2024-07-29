package com.example.petdiary.services.calendar

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.os.Build
import android.provider.CalendarContract
import androidx.annotation.RequiresApi
import androidx.loader.content.AsyncTaskLoader
import com.example.petdiary.domain.model.Appointment
import java.time.ZoneId

class CalendarLoader(
    context: Context,
    private val calendarName: String,
    private val accountName: String
) : AsyncTaskLoader<Long>(context) {

    override fun loadInBackground(): Long? {
        val calendarId = getCalendarId()
        if (calendarId == -1L) {
            createCalendar()
            return getCalendarId()
        }
        return calendarId
    }

    @SuppressLint("Range")
    private fun getCalendarId(): Long {
        val cursor: Cursor? = context.contentResolver.query(
            CalendarContract.Calendars.CONTENT_URI,
            arrayOf(CalendarContract.Calendars._ID),
            "${CalendarContract.Calendars.NAME} = ?",
            arrayOf(calendarName),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                return it.getLong(it.getColumnIndex(CalendarContract.Calendars._ID))
            }
        }
        return -1L
    }

    private fun createCalendar() {
        val contentValues = ContentValues().apply {
            put(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            put(CalendarContract.Calendars.ACCOUNT_TYPE, "com.google")
            put(CalendarContract.Calendars.NAME, calendarName)
            put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, calendarName)
            put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
            put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER)
            put(CalendarContract.Calendars.OWNER_ACCOUNT, accountName)
            put(CalendarContract.Calendars.VISIBLE, 1)
            put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        }

        val uri = CalendarContract.Calendars.CONTENT_URI.buildUpon()
            .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, accountName)
            .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, "com.google")
            .build()

        context.contentResolver.insert(uri, contentValues)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addEventToCalendar(appointment: Appointment) {
        val values = ContentValues().apply {
            put(CalendarContract.Events.DTSTART, appointment.startDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            put(CalendarContract.Events.DTEND, appointment.endDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            put(CalendarContract.Events.TITLE, appointment.title)
            put(CalendarContract.Events.DESCRIPTION, appointment.description)
            put(CalendarContract.Events.CALENDAR_ID, getCalendarId())
            put(CalendarContract.Events.EVENT_TIMEZONE, ZoneId.systemDefault().id)
        }

        context.contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)
    }
}
