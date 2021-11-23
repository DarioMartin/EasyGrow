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

    fun dateToString(format: String, date: Long): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(date)
    }

    fun stringToCalendar(format: String, date: String): Calendar {
        val calendar = Calendar.getInstance()
        return calendar.apply {
            try {
                SimpleDateFormat(format, Locale.getDefault()).parse(date)?.let { time = it }
            } catch (e: Exception) {
            }
        }
    }

}
