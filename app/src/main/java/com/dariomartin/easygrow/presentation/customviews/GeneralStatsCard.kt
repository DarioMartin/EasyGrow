package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.GeneralStatisticsLayoutBinding
import com.dariomartin.easygrow.presentation.patient.statistics.GeneralStatistics


class GeneralStatsCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: GeneralStatisticsLayoutBinding =
        GeneralStatisticsLayoutBinding.inflate(LayoutInflater.from(context), this, true)


    fun updateGeneralStatistics(generalStatistics: GeneralStatistics) {
        binding.averageTimeStat.timeHour.text =
            context.getString(R.string.single_hour_format, generalStatistics.averageTimeHour)
        binding.averageTimeStat.timeMinute.text =
            context.getString(R.string.single_hour_format, generalStatistics.averageTimeMinute)

        binding.usedPens.text = generalStatistics.usedPens.toString()

        binding.totalGrowthStat.height.text = generalStatistics.totalGrowth.toString()

    }
}