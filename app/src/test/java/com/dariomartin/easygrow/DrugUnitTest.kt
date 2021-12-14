package com.dariomartin.easygrow

import com.dariomartin.easygrow.data.model.Concentration
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.utils.Extensions.round
import org.junit.Assert.assertEquals
import org.junit.Test

class DrugUnitTest {
    @Test
    fun test_calculateDose() {
        val drug = Drug(
            concentration = Concentration(5F, 1.5F)
        )
        assertEquals(drug.calculateDoseMl(0.1F).round(2), 0.03F)
    }

    @Test
    fun test_volumeString_1() {
        val drug = Drug(
            cartridgeVolume = 1.66F
        )
        assertEquals(drug.getCartridgeVolumeString(), "1,66 ml")
    }

    @Test
    fun test_volumeString_2() {
        val drug = Drug(
            cartridgeVolume = 0.066F
        )
        assertEquals(drug.getCartridgeVolumeString(), "0,07 ml")
    }

    @Test
    fun test_volumeString_3() {
        val drug = Drug(
            cartridgeVolume = 1F
        )
        assertEquals(drug.getCartridgeVolumeString(), "1 ml")
    }
}