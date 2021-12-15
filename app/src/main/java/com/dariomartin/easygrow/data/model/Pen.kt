package com.dariomartin.easygrow.data.model

import java.util.*

data class Pen(
    val id: String = "",
    var startingDate: Calendar? = null,
    var endDate: Calendar? = null,
    val drug: String? = null,
    val cartridgeVolume: Float = 0F,
    var volumedConsumed: Float = 0F
) {
    fun subtractDose(doseVolume: Float) {
        if (doseVolume < 0F) {
            throw Exception("Dose volume should be a positive value")
        }

        if (volumedConsumed + doseVolume > cartridgeVolume) {
            throw Exception("Dose volume is greater than remaining volume")
        }

        volumedConsumed += doseVolume

        if (startingDate == null) startingDate = Calendar.getInstance()
        if (getRemainingDoses(doseVolume) == 0) endDate = Calendar.getInstance()
    }

    fun getRemainingDoses(doseVolume: Float): Int {
        if (doseVolume < 0F) {
            throw Exception("Dose volume should be a positive value")
        }

        return ((cartridgeVolume - volumedConsumed) / doseVolume).toInt()
    }
}