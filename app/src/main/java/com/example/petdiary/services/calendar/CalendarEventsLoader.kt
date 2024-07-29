package com.example.petdiary.services.calendar

import android.content.Context
import android.provider.CalendarContract
import androidx.loader.content.CursorLoader

class CalendarEventsLoader(
    context: Context,
    private val calendarId: Long
) : CursorLoader(
    context,
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
