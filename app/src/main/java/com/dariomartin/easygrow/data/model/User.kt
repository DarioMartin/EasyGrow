package com.dariomartin.easygrow.data.model

abstract class User {
    enum class Type { PATIENT, SANITARY }

    abstract var id: String
    abstract var email: String
    abstract var type: Type
}