package com.dariomartin.easygrow

import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.presentation.sanitary.treatmentupdate.TreatmentForm
import org.junit.Test
import java.util.*

class TreatmentFormUnitTest {
    @Test
    fun testCorrectForm() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0.01F,
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testFormMissingDrug() {
        val form = TreatmentForm(
            dose = 0.01F,
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testFormMissingDose() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testFormMissingPens() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0.01F,
            date = Calendar.getInstance()
        )

        assert(form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testFormMissingDate() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0.01F,
            pens = 3,
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testInvalidDose_1() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = -0.34F,
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testInvalidDose_2() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0F,
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testInvalidDrug() {
        val form = TreatmentForm(
            drug = "Saizen",
            dose = 0.05F,
            pens = 3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testInvalidPens_1() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0.05F,
            pens = -3,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

    @Test
    fun testInvalidPens_2() {
        val form = TreatmentForm(
            drug = "Omnitrope",
            dose = 0.05F,
            pens = 15,
            date = Calendar.getInstance()
        )

        assert(!form.isValid(listOf(Drug(name = "Omnitrope"))))
    }

}