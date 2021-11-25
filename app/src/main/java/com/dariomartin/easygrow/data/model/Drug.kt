package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.utils.Extensions.float

data class Drug(
    val name: String,
    val pharmacy: String,
    val density: Density = Density(),
    val url: String
) {
    fun calculateDoseMl(requiredDoseMg: Measure): Measure {
        if (requiredDoseMg.unit != MeasureUnit.MILLIGRAM) throw IllegalArgumentException("Expecting mg value")
        val dose = requiredDoseMg.float() * density.volume.float() / density.mass.float()
        return Measure(dose, MeasureUnit.MILLILITER)
    }
}