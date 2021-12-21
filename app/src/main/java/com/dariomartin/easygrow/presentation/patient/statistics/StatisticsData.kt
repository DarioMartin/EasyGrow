package com.dariomartin.easygrow.presentation.patient.statistics

data class GeneralStatistics(
    var averageTimeHour: Int? = null,
    var averageTimeMinute: Int? = null,
    var usedPens: Int = 0,
    var totalGrowth: Int = 0
)

data class CurrentHeightData(
    val height: Int = 0,
    val last30Days: Int = 0
)
