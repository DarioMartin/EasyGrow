package com.dariomartin.easygrow.ui.dose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.model.Administration
import com.dariomartin.easygrow.model.Treatment
import com.dariomartin.easygrow.model.repository.IPatientRepository
import com.dariomartin.easygrow.utils.Extensions.float
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DosesViewModel @Inject constructor(private val patientRepository: IPatientRepository) :
    ViewModel() {

    val doses: MutableLiveData<Map<Administration.BodyPart, Boolean>> = MutableLiveData()
    private var newBodyPart: Administration.BodyPart? = null
    private var date: Calendar? = null
    val penDoses: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    init {
        getPreviousAdministrations()
    }

    private fun getPreviousAdministrations() {
        viewModelScope.launch {
            val treatment = patientRepository.getPatient().treatment

            val administrations = patientRepository.getAdministrations()
            val last3Administrations =
                administrations.sortedBy { it.date }.takeLast(3).map { it.bodyPart to true }.toMap()
                    .toMutableMap()
            newBodyPart?.let { last3Administrations[it] = true }
            doses.value = last3Administrations

            calculateRemainingDoses(treatment, administrations)
        }
    }

    private fun calculateRemainingDoses(
        treatment: Treatment,
        previousAdministrations: List<Administration>
    ) {
        val currentAdministrations =
            previousAdministrations.filter { it.date > treatment.date }.size
        val dosesPerPen = (treatment.drug.density.volume.float() / treatment.dose.float()).toInt()
        val consumedPens = currentAdministrations / dosesPerPen
        val remainingPens = treatment.totalPens - consumedPens
        val remainingDosesInPen = dosesPerPen - (currentAdministrations % dosesPerPen)

        penDoses.value = Pair(dosesPerPen, remainingDosesInPen)
    }

    fun newDose(bodyPart: Administration.BodyPart) {
        newBodyPart = when {
            bodyPart == newBodyPart -> null
            checkAvailability(bodyPart) -> {
                date = Calendar.getInstance()
                bodyPart
            }
            else -> newBodyPart
        }
        getPreviousAdministrations()
    }

    fun recordAdministration() {
        newBodyPart?.let { bodyPart ->
            date?.let { calendar ->
                patientRepository.recordAdministration(bodyPart, calendar)
            }
        }
    }

    private fun checkAvailability(bodyPart: Administration.BodyPart): Boolean {
        return !(doses.value?.get(bodyPart) ?: false)
    }

}