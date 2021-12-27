package com.dariomartin.easygrow.data.model

abstract class User {
    abstract fun missingRelevantData(): Boolean

    enum class Type { PATIENT, SANITARY }

    abstract var id: String
    abstract var email: String
    abstract var type: Type
}