package com.dariomartin.easygrow.utils

import android.icu.util.Measure

object Extensions {

    fun Measure.float() = this.number.toFloat()
}