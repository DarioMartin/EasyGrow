package com.dariomartin.easygrow.ui.patient

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.model.Patient

class ProfileViewModel : ViewModel() {

    private val patient: MutableLiveData<Patient> =
        MutableLiveData<Patient>()

    private val _text = MutableLiveData<String>().apply {
        value = "PEC1"
    }

    fun getPatient(): MutableLiveData<Patient> {

        return patient
    }
}