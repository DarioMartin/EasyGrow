package com.dariomartin.easygrow.data.dto

data class PenDTO(
    var id: String = "",
    val startingDate: String? = null,
    val endDate: String? = null,
    val drug: String? = null,
    val volumeConsumed: Float = 0F,
    val cartridgeVolume: Float = 0F
)