package com.dariomartin.easygrow.data.mapper

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
            treatment = treatmentDTO(patientDTO.treatment)
        )
    }

    private fun treatmentDTO(treatment: TreatmentDTO?): Treatment? {
        return null
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
            url = drug.url
        )
    }

    fun drugMapper(drug: Drug): DrugDTO {
        return DrugDTO(
            name = drug.name,
            pharmacy = drug.pharmacy,
            url = drug.url
        )
    }
}