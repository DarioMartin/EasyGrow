package com.dariomartin.easygrow.model

import java.util.*

data class Patient(
    override var email: String,
    var name: String,
    var surname: String,
    var photo: String,
    var height: Int,
    var birthday: Calendar,
    var weight: Float,
    var treatment: Treatment
) : User() {

    fun getAge(): Int {
        val today = Calendar.getInstance()


        var age = today[Calendar.YEAR] - birthday[Calendar.YEAR]

        if (today[Calendar.DAY_OF_YEAR] < birthday[Calendar.DAY_OF_YEAR]) {
            age--
        }

        return age
    }

}