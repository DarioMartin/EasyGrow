package com.dariomartin.easygrow.model

import java.util.*

data class Patient(
    override var email: String,
    var Name: String,
    var surname: String,
    var height: Int,
    var birthday: Date,
    var weight: Double,
    var treatment: Treatment
) : User()