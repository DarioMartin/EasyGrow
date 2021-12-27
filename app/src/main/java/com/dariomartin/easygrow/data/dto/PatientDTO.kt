package com.dariomartin.easygrow.data.dto

data class PatientDTO(
    var id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val birthday: String = "",
    val photo: String = "",
    val weight: Float = 0f,
    val height: Int = 0,
    val treatment: TreatmentDTO? = TreatmentDTO(),
)