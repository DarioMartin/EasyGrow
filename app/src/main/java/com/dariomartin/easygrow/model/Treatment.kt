package com.dariomartin.easygrow.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.utils.Extensions.float
import java.util.*

data class Treatment(
    var drug: Drug,
    var dose: Measure,
    val totalPens: Int = 0,
    val date: Calendar
) {
    data class Drug(
        val name: String,
        val pharmacy: String,
        val density: Density
    ) {
        fun calculateDoseMl(requiredDoseMg: Measure): Measure {
            if (requiredDoseMg.unit != MeasureUnit.MILLIGRAM) throw IllegalArgumentException("Expecting mg value")
            val dose = requiredDoseMg.float() * density.volume.float() / density.mass.float()
            return Measure(dose, MeasureUnit.MILLILITER)
        }
    }
}

class Density(val mass: Measure, val volume: Measure) {

}
