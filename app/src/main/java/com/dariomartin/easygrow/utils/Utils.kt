package com.dariomartin.easygrow.utils

import android.icu.util.MeasureUnit
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getMeasureUnitAbbreviation(unit: MeasureUnit): String {
        return when (unit) {
            MeasureUnit.MILLILITER -> "ml"
            MeasureUnit.DECILITER -> "dl"
            MeasureUnit.LITER -> "l"
            else -> ""
        }
    }

    fun dateToString(format: String, date: Long?): String {
        return try {
            SimpleDateFormat(format, Locale.getDefault()).format(date)
        } catch (e: IllegalArgumentException) {
            ""
        }
    }

    fun stringToCalendar(format: String, date: String): Calendar? {
        return try {
            val calendar: Calendar? = Calendar.getInstance()
            SimpleDateFormat(format, Locale.getDefault()).parse(date)?.let { calendar?.time = it }
            calendar
        } catch (e: Exception) {
            null
        }
    }

}
