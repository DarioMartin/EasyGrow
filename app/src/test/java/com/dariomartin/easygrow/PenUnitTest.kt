package com.dariomartin.easygrow

import com.dariomartin.easygrow.data.model.Pen
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

class PenUnitTest {
    @Test
    fun test_subtractValidDose() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 0F
        )
        pen.subtractDose(0.5F)
        Assert.assertEquals(pen.volumedConsumed, 0.5F)
    }

    @Test
    fun test_firstDose() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 0F
        )
        pen.subtractDose(0.5F)
        Assert.assertNotNull(pen.startingDate)
        Assert.assertNull(pen.endDate)
    }

    @Test
    fun test_lastDose() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 1F
        )
        pen.subtractDose(0.5F)
        Assert.assertNotNull(pen.startingDate)
        Assert.assertNotNull(pen.endDate)
    }

    @Test
    fun test_invalidDose_1() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 1F
        )

        try {
            pen.subtractDose(0.6F)
            Assert.fail()
        } catch (e: Exception) {
        }
    }

    @Test
    fun test_invalidDose_2() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 1F
        )

        try {
            pen.subtractDose(-0.5F)
            Assert.fail()
        } catch (e: Exception) {
        }
    }

    @Test
    fun test_getRemainingDoses_1() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 1F
        )

        Assert.assertEquals(pen.getRemainingDoses(0.2F), 2)
    }

    @Test
    fun test_getRemainingDoses_2() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 0F
        )

        Assert.assertEquals(pen.getRemainingDoses(0.2F), 7)
    }

    @Test
    fun test_getInvalidRemainingDoses() {
        val pen = Pen(
            cartridgeVolume = 1.5F,
            volumedConsumed = 0F
        )
        try {
            pen.getRemainingDoses(-0.2F)
            Assert.fail()
        } catch (e: java.lang.Exception) {

        }
    }
}