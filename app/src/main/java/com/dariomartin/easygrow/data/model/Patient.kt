package com.dariomartin.easygrow.data.model

import java.time.LocalDate
import java.time.Period
import java.util.*

data class Patient(
    override var id: String = "",
    override var type: Type = Type.PATIENT,
    override var email: String = "",
    var name: String = "",
    var surname: String = "",
    var photo: String = "",
    var height: Int = 0,
    var birthday: Calendar? = null,
    var weight: Float = 0F,
    var treatment: Treatment? = null
) : User() {

    fun getAge(): Int {
        return birthday?.let {
            Period.between(
                LocalDate.of(
                    it.get(Calendar.YEAR),
                    it.get(Calendar.MONTH) + 1,
                    it.get(Calendar.DAY_OF_MONTH)
                ),
                LocalDate.now()
            ).years
        } ?: 0
    }

    override fun missingRelevantData(): Boolean {
        return name.isEmpty() || surname.isEmpty() || height <= 0 || birthday == null || weight <= 0
    }

}