package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import java.util.*

data class Pen(
    val id: String = "",
    val startingDate: Calendar? = null,
    val endDate: Calendar? = null,
    val drug: Drug = Drug(),
    val volumedConsumed: Measure = Measure(0, MeasureUnit.MILLILITER)
) {

    fun totalApplications(dose: Measure): Int {
        return (drug.cartridgeVolume.number.toFloat() / dose.number.toFloat()).toInt()
    }

    fun remainingApplications(dose: Measure): Int {
        val remainingVolume =
            drug.cartridgeVolume.number.toFloat() - volumedConsumed.number.toFloat()
        return (remainingVolume / dose.number.toFloat()).toInt()
    }

}