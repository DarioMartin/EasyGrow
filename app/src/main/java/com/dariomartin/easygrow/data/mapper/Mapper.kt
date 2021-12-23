package com.dariomartin.easygrow.data.mapper

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import com.dariomartin.easygrow.data.dto.*
import com.dariomartin.easygrow.data.model.*
import com.dariomartin.easygrow.utils.Utils.DateFormat.DD_MMM_YYYY_HH_MM
import com.dariomartin.easygrow.utils.Utils.DateFormat.DD_MM_YYYY
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
            birthday = stringToCalendar(DD_MM_YYYY, patientDTO.birthday),
            weight = patientDTO.weight,
            treatment = patientDTO.treatment?.let { treatmentDTOMapper(it) }
        )
    }

    private fun treatmentDTOMapper(treatment: TreatmentDTO): Treatment {
        return Treatment(
            drug = treatment.drug,
            lastUpdate = stringToCalendar(DD_MMM_YYYY_HH_MM, treatment.lastReview)
                ?: Calendar.getInstance(),
            dose = treatment.dose,
        )
    }

    private fun treatmentMapper(treatment: Treatment?): TreatmentDTO {
        return if (treatment == null) TreatmentDTO()
        else TreatmentDTO(
            drug = treatment.drug ?: "",
            dose = treatment.dose,
            lastReview = dateToString(DD_MMM_YYYY_HH_MM, treatment.lastUpdate.timeInMillis)
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
            birthday = dateToString(DD_MM_YYYY, patient.birthday?.timeInMillis),
            weight = patient.weight,
            treatment = treatmentMapper(patient.treatment)
        )
    }

    fun administrationDtoMapper(adminDto: AdministrationDTO): Administration {
        val date = stringToCalendar(DD_MMM_YYYY_HH_MM, adminDto.date) ?: Calendar.getInstance()
        return Administration(
            date = date,
            bodyPart = BodyPart.valueOf(adminDto.bodyPart)
        )
    }

    fun administrationMapper(admin: Administration): AdministrationDTO {
        return AdministrationDTO(
            date = dateToString(DD_MMM_YYYY_HH_MM, admin.date.timeInMillis),
            bodyPart = admin.bodyPart.name
        )
    }

    fun drugDtoMapper(drug: DrugDTO): Drug {
        return Drug(
            name = drug.name,
            pharmacy = drug.pharmacy,
            concentration = Concentration(
                mass = drug.amountOfDrugMg,
                volume = drug.volumeOfDrugMl
            ),
            cartridgeVolume = drug.cartridgeVolumeMl,
            url = drug.url
        )
    }

    fun drugMapper(drug: Drug): DrugDTO {
        return DrugDTO(
            id = drug.name,
            name = drug.name,
            pharmacy = drug.pharmacy,
            amountOfDrugMg = drug.concentration.mass,
            volumeOfDrugMl = drug.concentration.volume,
            cartridgeVolumeMl = drug.cartridgeVolume,
            url = drug.url
        )
    }

    fun penDtoMapper(penDto: PenDTO): Pen {
        val startingDate = penDto.startingDate?.let { stringToCalendar(DD_MMM_YYYY_HH_MM, it) }
            ?: Calendar.getInstance()
        val endDate = penDto.endDate?.let { stringToCalendar(DD_MMM_YYYY_HH_MM, it) }
            ?: Calendar.getInstance()

        return Pen(
            id = penDto.id,
            startingDate = startingDate,
            endDate = endDate,
            drug = penDto.drug,
            volumedConsumed = penDto.volumeConsumed,
            cartridgeVolume = penDto.cartridgeVolume
        )
    }

    fun penMapper(pen: Pen): PenDTO {
        return PenDTO(
            id = pen.id,
            startingDate = pen.startingDate?.let {
                dateToString(DD_MM_YYYY, it.timeInMillis)
            },
            endDate = pen.endDate?.let {
                dateToString(DD_MM_YYYY, it.timeInMillis)
            },
            drug = pen.drug,
            volumeConsumed = pen.volumedConsumed,
            cartridgeVolume = pen.cartridgeVolume
        )
    }

    fun heightMeasureMapper(heightMeasure: HeightMeasure): HeightMeasureDTO {
        return HeightMeasureDTO(
            height = heightMeasure.height,
            date = heightMeasure.date?.timeInMillis ?: 0
        )
    }

    fun heightMeasureDtoMapper(dto: HeightMeasureDTO): HeightMeasure {
        val calendar = Calendar.getInstance()
        calendar.time = Date(dto.date)

        return HeightMeasure(
            height = dto.height,
            date = calendar
        )
    }
}