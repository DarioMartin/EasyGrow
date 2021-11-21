package com.dariomartin.easygrow.utils

import android.icu.util.Measure

object Extensions {

    fun Measure.float() = this.number.toFloat()
    fun Boolean.toInt(): Int = if (this) 1 else 0
}