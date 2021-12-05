package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.utils.Extensions.float
import com.dariomartin.easygrow.utils.Extensions.niceDecimalNumber

data class Drug(
    val name: String = "",
    val pharmacy: String = "",
    val concentration: Concentration = Concentration(),
    val cartridgeVolume: Measure = Measure(0, MeasureUnit.MILLILITER),
    val url: String = ""
) {
    fun calculateDoseMl(requiredDoseMg: Measure): Measure {
        if (requiredDoseMg.unit != MeasureUnit.MILLIGRAM) throw IllegalArgumentException("Expecting mg value")
        val dose =
            requiredDoseMg.float() * concentration.volume.float() / concentration.mass.float()
        return Measure(dose, MeasureUnit.MILLILITER)
    }

    fun getCartridgeVolumeString(): String {
        return "${cartridgeVolume.niceDecimalNumber()} ml"
    }

    fun getConcentrationString(): String {
        val mass: String = concentration.mass.niceDecimalNumber()
        val volume =
            if (concentration.volume.number == 1F) ""
            else "${concentration.volume.niceDecimalNumber()} "

        return "$mass mg/${volume}ml"
    }
}