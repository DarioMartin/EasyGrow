package com.dariomartin.easygrow.model.repository

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.model.Administration
import com.dariomartin.easygrow.model.Density
import com.dariomartin.easygrow.model.Patient
import com.dariomartin.easygrow.model.Treatment
import java.util.*
import javax.inject.Inject
import kotlin.random.Random.Default.nextInt

class PatientRepositoryMock @Inject constructor() : IPatientRepository {
    override suspend fun getPatient(): Patient {
        val patientWeight = 30f
        return Patient(
            email = "patient@email.com",
            name = "Neville",
            surname = "Griffin",
            photo = "https://image.shutterstock.com/image-photo/funny-small-child-denim-tshirt-260nw-1008710635.jpg",
            height = 130,
            birthday = getBirthDay(),
            weight = patientWeight,
            treatment = getTreatment(patientWeight)
        )
    }

    override suspend fun getAdministrations(): List<Administration> {

        val numAdministrations = 5000

        val refCal = Calendar.getInstance()
        refCal.add(Calendar.DATE, -(numAdministrations + 1))

        val doses = mutableListOf<Administration>()

        for (i in 1..numAdministrations) {
            val cal = Calendar.getInstance()
            cal.time = refCal.time
            cal.set(Calendar.HOUR, nextInt(20, 22))
            cal.set(Calendar.MINUTE, nextInt(0, 59))
            val part = i % Administration.BodyPart.values().size
            doses.add(Administration(cal, Administration.BodyPart.values()[part]))
            refCal.add(Calendar.DATE, 1)
        }

        return doses
    }

    override fun recordAdministration(newBodyPart: Administration.BodyPart, date: Calendar) {

    }

    private fun getTreatment(patientWeight: Float): Treatment {
        val drug = Treatment.Drug(
            "Omnitrope", "Sandoz", density = Density(
                Measure(10f, MeasureUnit.MILLIGRAM), Measure(1.5f, MeasureUnit.MILLILITER)
            )
        )

        val doseMeasure =
            drug.calculateDoseMl(Measure(0.035f * patientWeight, MeasureUnit.MILLIGRAM))
        val dose = Measure(doseMeasure.number, MeasureUnit.MILLILITER)
        val lastDate = Calendar.getInstance()
        lastDate.add(Calendar.DATE, -15)
        return Treatment(drug = drug, dose = dose, totalPens = 5, date = lastDate)
    }

    private fun getBirthDay(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(2015, 4, 3)
        return cal
    }

}