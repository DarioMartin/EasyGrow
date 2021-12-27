package com.dariomartin.easygrow.presentation.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment() {

    private var listener: DatePickerDialog.OnDateSetListener? = null
    private var c: Calendar = Calendar.getInstance()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireActivity(), listener, year, month, day)
    }

    companion object {
        fun newInstance(
            initialDate: Calendar?,
            listener: DatePickerDialog.OnDateSetListener
        ): DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.listener = listener
            fragment.c = initialDate ?: Calendar.getInstance()
            return fragment
        }
    }
}
