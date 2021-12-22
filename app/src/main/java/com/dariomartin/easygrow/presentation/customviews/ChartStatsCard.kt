package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.data.model.HeightMeasure
import com.dariomartin.easygrow.databinding.ChartStatisticsLayoutBinding
import java.util.*


class ChartStatsCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ChartStatisticsLayoutBinding =
        ChartStatisticsLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateChartData(heightChartData: List<HeightMeasure>) {
        val thisYear = Calendar.getInstance()
        thisYear.set(Calendar.DAY_OF_YEAR, 1)

        val data: MutableList<Pair<String, Float>> = mutableListOf()

        var last = 0F

        for (month in 0..11) {
            val value: Float = heightChartData.filter {
                it.date?.get(Calendar.YEAR) == thisYear.get(Calendar.YEAR) &&
                        it.date?.get(Calendar.MONTH) == month
            }.map { it.height }.average().toFloat()

            val monthValue = if (value.isNaN()) last else value
            last = monthValue

            data.add(Pair(month.toString(), monthValue))
        }

        binding.title.text = thisYear.get(Calendar.YEAR).toString()
        binding.chart.show(data)
    }
}