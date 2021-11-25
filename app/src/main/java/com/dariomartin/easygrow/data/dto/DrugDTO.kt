package com.dariomartin.easygrow.data.dto

data class DrugDTO(
    var id:String = "",
    val name: String = "",
    val pharmacy: String = "",
    val mass: Int = 0,
    val volume: Int = 0,
    val url: String = ""
)
