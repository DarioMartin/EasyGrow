package com.dariomartin.easygrow.data.dto

data class DrugDTO(
    var id: String = "",
    val name: String = "",
    val pharmacy: String = "",
    val amountOfDrugMg: Float = 0F,
    val volumeOfDrugMl: Float = 0F,
    val cartridgeVolumeMl: Float = 0F,
    val url: String = ""
)
