package com.dariomartin.easygrow.data.dto

data class PenDTO(
    var id: String = "",
    val startingDate: String? = null,
    val endDate: String? = null,
    val drug: String? = null,
    val volumedConsumed: Float = 0F
)