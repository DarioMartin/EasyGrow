package com.dariomartin.easygrow.utils

import android.icu.util.MeasureUnit
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    enum class DateFormat(val format: String) {
        DD_MM_YYYY("dd/MM/yyyy"),
        DD_MMM_YYYY("dd MMM yyyy"),
        DD_MMM_YYYY_HH_MM("dd/MM/yyyy HH:mm"),
        HH_MM("HH:mm")
    }

    fun getMeasureUnitAbbreviation(unit: MeasureUnit): String {
        return when (unit) {
            MeasureUnit.MILLILITER -> "ml"
            MeasureUnit.DECILITER -> "dl"
            MeasureUnit.LITER -> "l"
            else -> ""
        }
    }

    fun dateToString(dateFormat: DateFormat, date: Long?): String {
        return try {
            SimpleDateFormat(dateFormat.format, Locale.getDefault()).format(date)
        } catch (e: IllegalArgumentException) {
            ""
        }
    }

    fun stringToCalendar(dateFormat: DateFormat, date: String?): Calendar? {
        return try {
            val calendar: Calendar? = Calendar.getInstance()
            SimpleDateFormat(dateFormat.format, Locale.getDefault()).parse(date)
                ?.let { calendar?.time = it }
            calendar
        } catch (e: Exception) {
            null
        }
    }

}
