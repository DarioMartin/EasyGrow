package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import java.util.*

data class Pen(
    val id: String = "",
    val startingDate: Calendar? = null,
    val endDate: Calendar? = null,
    val drug: String? = null,
    val volumedConsumed: Measure = Measure(0, MeasureUnit.MILLILITER)
)