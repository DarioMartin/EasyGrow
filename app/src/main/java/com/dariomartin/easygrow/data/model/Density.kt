package com.dariomartin.easygrow.data.model

import android.icu.util.Measure
import android.icu.util.MeasureUnit

class Density(
    val mass: Measure = Measure(0, MeasureUnit.MILLIGRAM),
    val volume: Measure = Measure(0, MeasureUnit.MILLILITER)
)