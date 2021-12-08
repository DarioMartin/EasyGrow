package com.dariomartin.easygrow.presentation.patient.reminder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.injecton.EGPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(val pref: EGPreferences) : ViewModel() {

    val time = MutableLiveData<Calendar>()

    init {
        setUpCalendar()
    }

    private fun setUpCalendar() {
        val calendar: Calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, pref.getHour())
        calendar.set(Calendar.MINUTE, pref.getMinutes())
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        time.value = calendar
    }

    fun setNewTime(hour: Int, minutes: Int) {
        pref.saveHour(hour)
        pref.saveMinutes(minutes)
        setUpCalendar()
    }

}