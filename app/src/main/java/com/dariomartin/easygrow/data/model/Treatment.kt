package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import java.util.*

data class Treatment(
    var drug: String? = null,
    var dose: Measure = Measure(0, MeasureUnit.MILLILITER),
    val lastUpdate: Calendar,
    var pens: MutableList<Pen> = mutableListOf()
)
