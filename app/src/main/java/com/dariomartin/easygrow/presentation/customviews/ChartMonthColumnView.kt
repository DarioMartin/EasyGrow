package com.dariomartin.easygrow.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.databinding.ChartMonthColumnItemBinding


class ChartMonthColumnView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: ChartMonthColumnItemBinding =
        ChartMonthColumnItemBinding.inflate(LayoutInflater.from(context), this, true)

    fun setData(value: Int, max: Int, month: String) {
        binding.progress.max = max
        binding.progress.progress = value
        binding.label.text = month
    }
}