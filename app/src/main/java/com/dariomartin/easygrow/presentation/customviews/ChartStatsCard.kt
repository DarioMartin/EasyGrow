package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.HeightMeasure
import com.dariomartin.easygrow.databinding.ChartStatisticsLayoutBinding
import com.dariomartin.easygrow.utils.Utils
import java.util.*


class ChartStatsCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ChartStatisticsLayoutBinding =
        ChartStatisticsLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateChartData(heightChartData: List<HeightMeasure>) {
        binding.chart.removeAllViews()

        val thisYear = Calendar.getInstance()
        thisYear.set(Calendar.DAY_OF_YEAR, 1)

        binding.title.text =
            context.getString(R.string.year_progress_title, thisYear.get(Calendar.YEAR))

        val yearData = getYearData(heightChartData, thisYear.get(Calendar.YEAR))

        binding.line.isVisible = yearData.isNotEmpty()
        binding.medium.isVisible = yearData.isNotEmpty()


        val maxHeight = yearData.maxOf { d -> d.second }
        val maxValue = maxHeight + 5
        val minHeight = yearData.minOf { d -> d.second }
        val minValue = minHeight - 10
        val refValue = (minHeight + maxHeight) / 2

        val ref = normalise(refValue, minValue, maxValue)+0.05F
        val params = binding.line.layoutParams as LayoutParams
        params.verticalBias = 1 - ref
        binding.line.layoutParams = params
        binding.medium.text =
            context.getString(
                R.string.height_cm_format,
                refValue.toInt()
            )

        yearData.forEach {
            val column = ChartMonthColumnView(context)

            val value = it.second


            if (maxHeight <= 0) {
                column.setData(0, 0, it.first)
            } else {
                val normalised: Float = normalise(value, minValue, maxValue)
                column.setData((normalised * 100).toInt(), 100, it.first)
            }

            val param = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                1.0f
            )

            column.layoutParams = param

            binding.chart.addView(column)
        }
    }

    private fun normalise(value: Float, min: Float, max: Float): Float {
        val normalised: Float = (value - min) / (max - min)
        return normalised
    }

    private fun getYearData(
        heightChartData: List<HeightMeasure>,
        year: Int
    ): List<Pair<String, Float>> {
        val data = mutableListOf<Pair<String, Float>>()
        var last = 0F

        val today = Calendar.getInstance()

        for (month in 0..11) {
            var monthValue = 0F

            if (today.get(Calendar.YEAR) != year || today.get(Calendar.MONTH) >= month) {
                val monthMeasures: List<Int> = heightChartData.filter {
                    it.date?.get(Calendar.YEAR) == year && it.date?.get(Calendar.MONTH) == month
                }.map { it.height }
                val monthAverage = monthMeasures.average().toFloat()
                monthValue = if (monthAverage.isNaN()) last else monthAverage
                last = monthValue
            }

            data.add(Pair(Utils.getMonthName(month).first().toString(), monthValue))
        }

        return data
    }
}