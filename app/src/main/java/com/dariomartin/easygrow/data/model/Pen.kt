package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import java.util.*

data class Pen(
    val id: String = "",
    var startingDate: Calendar? = null,
    var endDate: Calendar? = null,
    val drug: String? = null,
    val cartridgeVolume: Measure = Measure(0, MeasureUnit.MILLILITER),
    var volumedConsumed: Measure = Measure(0, MeasureUnit.MILLILITER)
) {
    fun subtractDose(doseVolume: Float) {
        val n = volumedConsumed.number.toFloat() + doseVolume
        volumedConsumed = Measure(n, MeasureUnit.MILLILITER)

        if (startingDate == null) startingDate = Calendar.getInstance()
        if (getRemainingDoses(doseVolume) == 0) endDate = Calendar.getInstance()
    }

    fun getRemainingDoses(doseVolume: Float): Int {
        return ((cartridgeVolume.number.toFloat() - volumedConsumed.number.toFloat()) / doseVolume).toInt()
    }
}