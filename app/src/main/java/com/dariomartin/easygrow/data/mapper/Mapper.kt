package com.dariomartin.easygrow.data.mapper

import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.utils.Utils.dateToString
import com.dariomartin.easygrow.utils.Utils.stringToCalendar

object Mapper {

    fun patientDtoMapper(patientDTO: PatientDTO, userId: String): Patient {
        return Patient(
            id = userId,
            name = patientDTO.name,
            surname = patientDTO.surname,
            email = patientDTO.email,
            type = User.Type.PATIENT,
            photo = patientDTO.photo,
            height = patientDTO.height,
            birthday = stringToCalendar("dd/MM/yyyy", patientDTO.birthday),
            weight = patientDTO.weight
        )
    }

    fun patientMapper(patient: Patient): PatientDTO {
        return PatientDTO(
            name = patient.name,
            surname = patient.surname,
            email = patient.email,
            photo = patient.photo,
            height = patient.height,
            birthday = dateToString("dd/MM/yyyy", patient.birthday.timeInMillis),
            weight = patient.weight
        )
    }

    fun administrationDtoMapper(adminDto: AdministrationDTO): Administration {
        val date = stringToCalendar("dd/MM/yyyy hh:mm", adminDto.date)
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
}