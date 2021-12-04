package com.dariomartin.easygrow.data.model

import java.util.*

data class Patient(
    override var id: String,
    override var type: Type,
    override var email: String,
    var name: String,
    var surname: String,
    var photo: String,
    var height: Int,
    var birthday: Calendar?,
    var weight: Float,
    var treatment: Treatment? = null
) : User() {

    fun getAge(): Int {
        val today = Calendar.getInstance()
        var age = 0
        birthday?.let {
            age = today[Calendar.YEAR] - it.get(Calendar.YEAR)
            if (today[Calendar.DAY_OF_YEAR] < it.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
        }
        return age
    }

    override fun missingData(): Boolean {
        return name.isEmpty() || surname.isEmpty() || height <= 0 || birthday == null || weight <= 0
    }

}