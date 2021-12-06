package com.dariomartin.easygrow.presentation.sanitary.treatmentupdate

import com.dariomartin.easygrow.data.model.Drug
import java.util.*

data class TreatmentForm(
    var drug: String? = null,
    var dose: Float = 0F,
    var date: Calendar? = null
) {
    fun isValid(drugs: List<Drug>) = isValidDrug(drugs)
            && isValidDose()

    fun isValidDrug(drugs: List<Drug>) = drugs.map { it.name }.contains(drug)
    fun isValidDose() = dose > 0
}
