package com.dariomartin.easygrow.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.PenviewLayoutBinding


class PenView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: PenviewLayoutBinding =
        PenviewLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun setDoses(total: Int, remaining: Int) {
        binding.doses.removeAllViews()

        for (i in 1..remaining) {
            LayoutInflater.from(context).inflate(R.layout.dose_item, binding.doses)
        }

        for (i in 1..(total - remaining)) {
            LayoutInflater.from(context).inflate(R.layout.dose_item_empty, binding.doses)
        }
    }
}