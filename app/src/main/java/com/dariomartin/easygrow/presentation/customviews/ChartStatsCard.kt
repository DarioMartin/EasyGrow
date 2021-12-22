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

        val yearData = getYearData(heightChartData, thisYear)

        binding.line.isVisible = yearData.isNotEmpty()
        binding.medium.isVisible = yearData.isNotEmpty()
        binding.medium.text =
            context.getString(
                R.string.height_cm_format,
                (yearData.maxOf { d -> d.second } / 2).toInt()
            )

        yearData.forEach {
            val column = ChartMonthColumnView(context)
            column.setData(it.second.toInt(), yearData.maxOf { d -> d.second }.toInt(), it.first)

            val param = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                1.0f
            )

            column.layoutParams = param

            binding.chart.addView(column)
        }
    }

    private fun getYearData(
        heightChartData: List<HeightMeasure>,
        thisYear: Calendar
    ): List<Pair<String, Float>> {
        val data = mutableListOf<Pair<String, Float>>()
        var last = 0F

        for (month in 0..11) {
            val value: Float = heightChartData.filter {
                it.date?.get(Calendar.YEAR) == thisYear.get(Calendar.YEAR) &&
                        it.date?.get(Calendar.MONTH) == month
            }.map { it.height }.average().toFloat()

            val monthValue = if (value.isNaN()) last else value
            last = monthValue

            data.add(Pair(Utils.getMonthName(month).first().toString(), monthValue))
        }

        return data
    }
}