package com.dariomartin.easygrow.data.dto

data class DrugDTO(
    var id: String = "",
    val name: String = "",
    val pharmacy: String = "",
    val amountOfDrugMg: Int = 0,
    val volumeOfDrugMl: Int = 0,
    val cartridgeVolumeMl: Int = 0,
    val url: String = ""
)
