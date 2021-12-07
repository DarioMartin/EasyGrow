package com.dariomartin.easygrow.presentation.sanitary.treatmentupdate

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.*
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.Pen
import com.dariomartin.easygrow.data.model.Treatment
import com.dariomartin.easygrow.data.repository.IDrugRepository
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreatmentUpdateViewModel @Inject constructor(
    private val patientRepository: IPatientRepository,
    private val drugRepository: IDrugRepository
) : ViewModel() {

    val successfulUpdate: MutableLiveData<Boolean> = MutableLiveData(false)
    var availableDrugs: List<Drug> = listOf()
    private var patient: Patient? = null

    fun getPatient(patientId: String): LiveData<Patient> {
        return patientRepository.getLivePatient(patientId).map {
            this.patient = it
            it
        }
    }

    fun getDrugs(): LiveData<List<Drug>> {
        return drugRepository.getDrugs().map {
            availableDrugs = it
            it
        }
    }

    fun updateTreatment(patientId: String, form: TreatmentForm) {
        viewModelScope.launch {
            patient?.apply {
                treatment = Treatment(
                    drug = form.drug,
                    dose = Measure(form.dose, MeasureUnit.MILLILITER),
                    lastUpdate = Calendar.getInstance()
                )
            }?.let {
                patientRepository.updatePatient(patientId, it)
                for (i in 1..form.pens) {
                    addPen(patientId, it.treatment?.drug)
                }
            }
            successfulUpdate.postValue(true)
        }
    }

    private suspend fun addPen(patientId: String, drug: String?) {
        val selectedDrug = availableDrugs.find { it.name == drug } ?: return

        val newPen = Pen(
            drug = selectedDrug.name,
            cartridgeVolume = selectedDrug.cartridgeVolume
        )
        patientRepository.addPen(patientId, newPen)
    }

    fun resetPatientPens(id: String) {
        patientRepository.removePens(id)
    }
}