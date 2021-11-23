package com.dariomartin.easygrow.utils

import android.icu.util.Measure
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

object Extensions {

    fun Measure.float() = this.number.toFloat()

    fun Boolean.toInt(): Int = if (this) 1 else 0

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }
}