package com.dariomartin.easygrow.presentation.patient.statistics

data class StatisticsData(
    val currentHeight: CurrentHeightData,
    val generalStatistics: GeneralStatistics
)

data class GeneralStatistics(
    val averageTimeHour: Int = 0,
    val averageTimeMinute: Int = 0,
    val usedPens: Int = 0,
    val totalGrowth: Int = 0
)

data class CurrentHeightData(
    val height: Int = 0,
    val last30Days: Int = 0
)
