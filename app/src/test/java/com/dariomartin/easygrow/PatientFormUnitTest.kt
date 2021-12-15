package com.dariomartin.easygrow

import com.dariomartin.easygrow.presentation.patient.profile.PatientForm
import org.junit.Assert
import org.junit.Test
import java.util.*

class PatientFormUnitTest {
    @Test
    fun test_correctForm() {
        val form = PatientForm(
            name = "John",
            surname = "Doe",
            weight = 45F,
            height = 130,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(form.isValid())
    }

    @Test
    fun test_missingName() {
        val form = PatientForm(
            surname = "Doe",
            weight = 45F,
            height = 130,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(!form.isValidName())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingSurname() {
        val form = PatientForm(
            name = "John",
            weight = 45F,
            height = 130,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(!form.isValidSurname())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingWeight() {
        val form = PatientForm(
            name = "John",
            surname = "Doe",
            height = 130,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(!form.isValidWeight())
        Assert.assertTrue(!form.isValid())
    }

    @Test
    fun test_missingHeight() {
        val form = PatientForm(
            name = "John",
            surname = "Doe",
            weight = 45F,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(!form.isValidHeight())
        Assert.assertTrue(!form.isValid())
    }
}