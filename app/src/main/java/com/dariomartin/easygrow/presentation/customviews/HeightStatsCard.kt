package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.HeightStatisticsLayoutBinding
import com.dariomartin.easygrow.presentation.patient.statistics.CurrentHeightData


class HeightStatsCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: HeightStatisticsLayoutBinding =
        HeightStatisticsLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateCurrentHeightData(heightData: CurrentHeightData) {
        binding.height.text = context.getString(R.string.height_cm_format, heightData.height)
        binding.subtitle.text = context.getString(R.string.last_30_days_height, heightData.last30Days)
    }
}