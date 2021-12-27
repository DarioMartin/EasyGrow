package com.dariomartin.easygrow.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import kotlin.math.roundToInt

object Extensions {

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun Float.niceDecimalNumber(): String {
        return DecimalFormat("#.##").format(this)
    }

    fun Double.niceDecimalNumber(): String {
        return DecimalFormat("#.##").format(this)
    }

    fun Float.round(decimals: Int): Float {
        var multiplier = 1F
        repeat(decimals) { multiplier *= 10 }
        return (this * multiplier).roundToInt() / multiplier
    }
}