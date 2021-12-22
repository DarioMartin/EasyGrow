package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.HeightStatisticsLayoutBinding
import com.dariomartin.easygrow.presentation.patient.statistics.HeightStatistics


class HeightStatsCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: HeightStatisticsLayoutBinding =
        HeightStatisticsLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateCurrentHeightData(heightStatistics: HeightStatistics) {
        binding.height.text = heightStatistics.height?.let {
            context.getString(R.string.height_cm_format, it)
        } ?: "-"
        binding.subtitle.text =
            context.getString(R.string.last_30_days_height, heightStatistics.last30Days)
    }
}