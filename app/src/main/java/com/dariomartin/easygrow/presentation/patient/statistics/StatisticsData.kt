package com.dariomartin.easygrow.presentation.patient.statistics

data class GeneralStatistics(
    var averageTimeHour: Int? = null,
    var averageTimeMinute: Int? = null,
    var usedPens: Int = 0,
    var totalGrowth: Int = 0
)

data class HeightStatistics(
    var height: Int? = null,
    var last12Months: Int = 0
)
