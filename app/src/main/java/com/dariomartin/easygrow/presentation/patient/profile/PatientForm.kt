package com.dariomartin.easygrow.presentation.patient.profile

import com.dariomartin.easygrow.data.model.User
import java.util.*

data class PatientForm(
    var image: String? = null,
    var name: String? = null,
    var surname: String? = null,
    var height: Int = 0,
    var weight: Float = 0F,
    var birthday: Calendar? = null,
) {
    fun isValid(type: User.Type) = isValidName()
            && isValidSurname()
            && isValidWeight(type)
            && isValidHeight(type)
            && isValidBirthday()

    fun isValidName() = !name.isNullOrEmpty()
    fun isValidSurname() = !surname.isNullOrEmpty()
    fun isValidWeight(type: User.Type) = weight > 0 || type == User.Type.PATIENT
    fun isValidHeight(type: User.Type) = height > 0 || type == User.Type.PATIENT
    fun isValidBirthday() = birthday != null
}
