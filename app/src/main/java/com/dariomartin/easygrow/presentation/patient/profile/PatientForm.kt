package com.dariomartin.easygrow.presentation.patient.profile

import java.util.*

data class PatientForm(
    var image: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var height: Int = 0,
    var weight: Float = 0F,
    var birthday: Calendar? = null,
) {
    fun isValid() = isValidName()
            && isValidSurname()
            && isValidWeight()
            && isValidHeight()
            && isValidBirthday()

    fun isValidName() = !name.isNullOrEmpty()
    fun isValidSurname() = !surname.isNullOrEmpty()
    fun isValidWeight() = weight > 0
    fun isValidHeight() = height > 0
    fun isValidBirthday() = birthday != null
}
