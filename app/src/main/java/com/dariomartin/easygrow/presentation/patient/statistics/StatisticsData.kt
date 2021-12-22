package com.dariomartin.easygrow.presentation.patient.statistics

import com.dariomartin.easygrow.data.model.HeightMeasure

data class GeneralStatistics(
    var averageTimeHour: Int? = null,
    var averageTimeMinute: Int? = null,
    var usedPens: Int = 0,
    var totalGrowth: Int = 0
)

data class HeightStatistics(
    var height: Int? = null,
    var last30Days: Int = 0
)
