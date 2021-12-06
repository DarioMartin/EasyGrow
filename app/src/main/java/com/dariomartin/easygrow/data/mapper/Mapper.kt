package com.dariomartin.easygrow.data.mapper

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.data.dto.*
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
            treatment = patientDTO.treatment?.let { treatmentDTOMapper(it) }
        )
    }

    private fun treatmentDTOMapper(treatment: TreatmentDTO): Treatment {
        return Treatment(
            drug = treatment.drug,
            lastUpdate = stringToCalendar("dd/MM/yyyy hh:mm", treatment.lastReview)
                ?: Calendar.getInstance(),
            dose = Measure(treatment.dose, MeasureUnit.MILLIGRAM),
        )
    }

    private fun treatmentMapper(treatment: Treatment?): TreatmentDTO {
        return if (treatment == null) TreatmentDTO()
        else TreatmentDTO(
            drug = treatment.drug ?: "",
            dose = treatment.dose.number.toFloat(),
            lastReview = dateToString("dd/MM/yyyy hh:mm", treatment.lastUpdate.timeInMillis)
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
            weight = patient.weight,
            treatment = treatmentMapper(patient.treatment)
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
            amountOfDrugMg = drug.concentration.mass.number.toFloat(),
            volumeOfDrugMl = drug.concentration.volume.number.toFloat(),
            cartridgeVolumeMl = drug.cartridgeVolume.number.toFloat(),
            url = drug.url
        )
    }

    fun penDtoMapper(penDto: PenDTO): Pen {
        val startingDate = penDto.startingDate?.let { stringToCalendar("dd/MM/yyyy hh:mm", it) }
            ?: Calendar.getInstance()
        val endDate = penDto.endDate?.let { stringToCalendar("dd/MM/yyyy hh:mm", it) }
            ?: Calendar.getInstance()

        return Pen(
            id = penDto.id,
            startingDate = startingDate,
            endDate = endDate,
            drug = Drug(name = penDto.drug),
            volumedConsumed = Measure(penDto.volumedConsumed, MeasureUnit.MILLILITER)
        )
    }

    fun penMapper(pen: Pen): PenDTO {
        return PenDTO(
            id = pen.id,
            startingDate = pen.startingDate?.let {
                dateToString("dd/MM/yyyy", it.timeInMillis)
            },
            endDate = pen.endDate?.let {
                dateToString("dd/MM/yyyy", it.timeInMillis)
            },
            drug = pen.drug.name,
            volumedConsumed = pen.volumedConsumed.number.toFloat()
        )
    }
}