package com.dariomartin.easygrow

import com.dariomartin.easygrow.data.model.Patient
import org.junit.Assert
import org.junit.Test
import java.util.*
import kotlin.random.Random.Default.nextInt

class PatientUnitTest {

    @Test
    fun test_calculateAge() {
        val age = nextInt(1, 10)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -age)
        val patient = Patient(birthday = calendar)

        Assert.assertEquals(patient.getAge(), age)
    }

    @Test
    fun test_missingRelevantData() {
        val patient = Patient()
        Assert.assertTrue(patient.missingRelevantData())
    }

    @Test
    fun test_noMissingRelevantData() {
        val patient = Patient(
            name = "John",
            surname = "Doe",
            height = 189,
            weight = 45F,
            birthday = Calendar.getInstance()
        )
        Assert.assertTrue(!patient.missingRelevantData())
    }
}