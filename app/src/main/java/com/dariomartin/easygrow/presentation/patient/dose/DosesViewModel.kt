package com.dariomartin.easygrow.presentation.patient.dose

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Treatment
import com.dariomartin.easygrow.data.repository.IPatientRepository
import com.dariomartin.easygrow.utils.Extensions.float
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DosesViewModel @Inject constructor(private val patientRepository: IPatientRepository) :
    ViewModel() {

    companion object {
        private const val MULTIPLE_ADMINS = true
    }

    val lastAdministrations: MutableLiveData<Map<BodyPart, Boolean>> = MutableLiveData()
    val penDoses: MutableLiveData<Pair<Int, Int>> = MutableLiveData()

    //private var newBodyParts: MutableList<BodyPart>? = null
    private var newAdministrations: MutableList<Administration> = mutableListOf()
    private var date: Calendar? = null

    init {
        getPreviousAdministrations()
    }

    private fun getPreviousAdministrations() {
        viewModelScope.launch {
            val treatment = patientRepository.getPatient(null)?.treatment

            val totalAdministrations: List<Administration> =
                //patientRepository.getAdministrations() +
                        newAdministrations

            val last3Administrations =
                totalAdministrations.sortedBy { it.date }.takeLast(3).map { it.bodyPart to true }
                    .toMap().toMutableMap()

            lastAdministrations.value = last3Administrations

            treatment?.let { calculateRemainingDoses(it, totalAdministrations) }
        }
    }

    private fun calculateRemainingDoses(
        treatment: Treatment,
        previousAdministrations: List<Administration>
    ) {
        var currentAdministrations =
            previousAdministrations.filter { it.date > treatment.lastUpdate }.size + newAdministrations.size
        val dosesPerPen = (treatment.drug.concentration.volume.float() / treatment.dose.float()).toInt()
        val consumedPens = currentAdministrations / dosesPerPen
        val remainingPens = treatment.totalPens - consumedPens
        val remainingDosesInPen = dosesPerPen - (currentAdministrations % dosesPerPen)

        penDoses.value = Pair(dosesPerPen, remainingDosesInPen)
    }

    fun newAdministration(bodyPart: BodyPart) {
        if (MULTIPLE_ADMINS) {
            newMultipleAdministration(bodyPart)
        } else {
            newSingleAdministration(bodyPart)
        }


        getPreviousAdministrations()
    }

    private fun newMultipleAdministration(bodyPart: BodyPart) {
        when {
            newAdministrations.isEmpty() || checkAvailability(bodyPart) -> {
                newAdministrations.add(Administration(Calendar.getInstance(), bodyPart))
            }
        }
    }

    private fun newSingleAdministration(bodyPart: BodyPart) {
        when {
            newAdministrations.isEmpty() && checkAvailability(bodyPart) -> {
                newAdministrations.add(Administration(Calendar.getInstance(), bodyPart))
            }
            newAdministrations.isNotEmpty() && checkAvailability(bodyPart) -> {
                newAdministrations.clear()
                newAdministrations.add(Administration(Calendar.getInstance(), bodyPart))
            }
            else -> {
                newAdministrations.clear()
            }
        }
    }

    private fun recordAdministration() {
        GlobalScope.launch {
            newAdministrations.forEach {
                patientRepository.addAdministration(it)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        recordAdministration()
    }

    private fun checkAvailability(bodyPart: BodyPart): Boolean {
        return !(lastAdministrations.value?.get(bodyPart) ?: false) &&
                newAdministrations.none { it.bodyPart == bodyPart }
    }

}

