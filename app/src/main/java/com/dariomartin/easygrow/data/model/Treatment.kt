package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import java.util.*

data class Treatment(
    var drug: Drug = Drug(),
    val totalPens: Int = 0,
    val lastUpdate: Calendar
) {

    private val pens: MutableList<Pen> = mutableListOf()
    var dose: Measure = Measure(0, MeasureUnit.MILLILITER)

    fun addPens(i: Int) {
        pens.addAll(List(i) { Pen() })
    }

    @JvmName("setDose1")
    fun setDose(measure: Measure) {
        val doseMeasure = drug.calculateDoseMl(measure)
        dose = Measure(doseMeasure.number, MeasureUnit.MILLILITER)
    }
}


