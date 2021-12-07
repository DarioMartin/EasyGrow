package com.dariomartin.easygrow.presentation.patient.dose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.dariomartin.easygrow.data.model.*
import com.dariomartin.easygrow.data.repository.IDrugRepository
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DosesViewModel @Inject constructor(
    private val patientRepository: IPatientRepository,
    private val drugRepository: IDrugRepository
) :
    ViewModel() {

    val penDoses = MediatorLiveData<Pair<Int, Int>>()

    private var newAdministration: Administration? = null
    private var date: Calendar? = null


    fun newAdministration(administration: Administration?) {
        this.newAdministration = administration
    }

    private fun recordAdministration() {
        GlobalScope.launch {
            newAdministration?.let { patientRepository.addAdministration(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        recordAdministration()
    }

    fun getPatient(): LiveData<Patient> {
        return patientRepository.getLivePatient()
    }

    fun calculateDoses(patient: Patient) {
        var pens: List<Pen> = listOf()
        var drug: Drug? = null

        val treatment = patient.treatment ?: return

        penDoses.addSource(drugRepository.getLiveDrug(patient.treatment?.drug ?: "")) { d ->
            drug = d
            drug?.let {
                penDoses.postValue(combine(treatment, it, pens))
            }
        }

        penDoses.addSource(patientRepository.getPens()) { p ->
            pens = p
            drug?.let {
                penDoses.postValue(combine(treatment, it, pens))
            }
        }
    }

    private fun combine(treatment: Treatment, drug: Drug, pens: List<Pen>): Pair<Int, Int> {
        val validPens = pens.filter { it.drug == drug.name }
        var currentPen = validPens.firstOrNull { it.startingDate != null }
        if (currentPen == null) currentPen = validPens.firstOrNull()

        if (currentPen == null) return Pair(0, 0)

        val totalDoses =
            (drug.cartridgeVolume.number.toFloat() / treatment.dose.number.toFloat()).toInt()

        val remainingVolume =
            drug.cartridgeVolume.number.toFloat() - currentPen.volumedConsumed.number.toFloat()
        val remainingDoses = (remainingVolume / treatment.dose.number.toFloat()).toInt()

        return Pair(totalDoses, remainingDoses)
    }

    fun getLastNAdministrations(n: Int): LiveData<List<Administration>> {
        return patientRepository.getAdministrations().map { list ->
            list.sortedByDescending { it.date }.take(n)
        }
    }

}

