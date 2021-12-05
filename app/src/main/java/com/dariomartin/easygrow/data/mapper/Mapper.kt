package com.dariomartin.easygrow.data.mapper

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.DrugDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.dto.TreatmentDTO
import com.dariomartin.easygrow.data.model.*
import com.dariomartin.easygrow.utils.Utils.dateToString
import com.dariomartin.easygrow.utils.Utils.stringToCalendar
import java.util.*

object Mapper {

    fun patientDtoMapper(patientDTO: PatientDTO): Patient {
        return Patient(
            id = patientDTO.id,
            name = patientDTO.name,
            surname = patientDTO.surname,
            email = patientDTO.email,
            type = User.Type.PATIENT,
            photo = patientDTO.photo,
            height = patientDTO.height,
            birthday = stringToCalendar("dd/MM/yyyy", patientDTO.birthday),
            weight = patientDTO.weight,
            treatment = patientDTO.treatment?.let { treatmentDTO(it) }
        )
    }

    private fun treatmentDTO(treatment: TreatmentDTO): Treatment {
        return Treatment(
            drug = Drug(name = treatment.drug),
            totalPens = treatment.totalPens,
            lastUpdate = stringToCalendar("dd/MM/yyyy hh:mm", treatment.lastReview)
                ?: Calendar.getInstance()
        )
    }

    fun patientMapper(patient: Patient): PatientDTO {
        return PatientDTO(
            id = patient.id,
            name = patient.name,
            surname = patient.surname,
            email = patient.email,
            photo = patient.photo,
            height = patient.height,
            birthday = dateToString("dd/MM/yyyy", patient.birthday?.timeInMillis),
            weight = patient.weight
        )
    }

    fun administrationDtoMapper(adminDto: AdministrationDTO): Administration {
        val date = stringToCalendar("dd/MM/yyyy hh:mm", adminDto.date) ?: Calendar.getInstance()
        return Administration(
            date = date,
            bodyPart = BodyPart.valueOf(adminDto.bodyPart)
        )
    }

    fun administrationMapper(admin: Administration): AdministrationDTO {
        return AdministrationDTO(
            date = dateToString("dd/MM/yyyy hh:mm", admin.date.timeInMillis),
            bodyPart = admin.bodyPart.name
        )
    }

    fun drugDtoMapper(drug: DrugDTO): Drug {
        return Drug(
            name = drug.name,
            pharmacy = drug.pharmacy,
            concentration = Concentration(
                mass = Measure(drug.amountOfDrugMg, MeasureUnit.MILLIGRAM),
                volume = Measure(drug.volumeOfDrugMl, MeasureUnit.MILLILITER)
            ),
            cartridgeVolume = Measure(drug.cartridgeVolumeMl, MeasureUnit.MILLILITER),
            url = drug.url
        )
    }

    fun drugMapper(drug: Drug): DrugDTO {
        return DrugDTO(
            id = drug.name,
            name = drug.name,
            pharmacy = drug.pharmacy,
            amountOfDrugMg = drug.concentration.mass.number.toInt(),
            volumeOfDrugMl = drug.concentration.volume.number.toInt(),
            cartridgeVolumeMl = drug.cartridgeVolume.number.toInt(),
            url = drug.url
        )
    }
}