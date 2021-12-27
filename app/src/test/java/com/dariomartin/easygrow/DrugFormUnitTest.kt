package com.dariomartin.easygrow

import com.dariomartin.easygrow.presentation.sanitary.createdrug.DrugForm
import org.junit.Assert
import org.junit.Test

class DrugFormUnitTest {
    @Test
    fun test_correctForm() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = 5F,
            drugVolume = 1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(form.isValid())
    }

    @Test
    fun test_invalidForm() {
        val form = DrugForm()
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingName() {
        val form = DrugForm(
            pharmacy = "Sandoz",
            drugMass = 5F,
            drugVolume = 1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidName())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingPharmacy() {
        val form = DrugForm(
            name = "Omnitrope",
            drugMass = 5F,
            drugVolume = 1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidPharmacy())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingMass() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugVolume = 1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidDrugMass())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingVolume() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = 5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidDrugVolume())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingCartridgeVolume() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = 5F,
            drugVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidCartridgeVolume())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_invalidDrugMass() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = -5F,
            drugVolume = 1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidDrugMass())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_invalidDrugVolume() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = 5F,
            drugVolume = -1.5F,
            cartridgeVolume = 1.5F
        )
        Assert.assertTrue(!form.isValidDrugVolume())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_invalidCartridgeVolume() {
        val form = DrugForm(
            name = "Omnitrope",
            pharmacy = "Sandoz",
            drugMass = 5F,
            drugVolume = 1.5F,
            cartridgeVolume = -1.5F
        )
        Assert.assertTrue(!form.isValidCartridgeVolume())
        Assert.assertTrue(!form.isValid())
    }
}