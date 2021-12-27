package com.dariomartin.easygrow.data.model

import com.dariomartin.easygrow.utils.Extensions.niceDecimalNumber

data class Drug(
    val name: String = "",
    val pharmacy: String = "",
    val concentration: Concentration = Concentration(),
    val cartridgeVolume: Float = 0F,
    val url: String = ""
) {
    fun calculateDoseMl(requiredDoseMg: Float): Float {
        return (requiredDoseMg * concentration.volume / concentration.mass)
    }

    fun getCartridgeVolumeString(): String {
        return "${cartridgeVolume.niceDecimalNumber()} ml"
    }

    fun getConcentrationString(): String {
        val mass: String = concentration.mass.niceDecimalNumber()
        val volume =
            if (concentration.volume == 1F) ""
            else "${concentration.volume.niceDecimalNumber()} "

        return "$mass mg/${volume}ml"
    }
}