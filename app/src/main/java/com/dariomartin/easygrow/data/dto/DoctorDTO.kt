package com.dariomartin.easygrow.data.dto

data class DoctorDTO(
    var id: String = "",
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val photo: String = "",
    val patients: List<String> = listOf()
)