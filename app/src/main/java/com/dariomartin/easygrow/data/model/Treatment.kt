package com.dariomartin.easygrow.data.model

import java.util.*

data class Treatment(
    var drug: String? = null,
    var dose: Float = 0F,
    val lastUpdate: Calendar,
    var pens: MutableList<Pen> = mutableListOf()
)
