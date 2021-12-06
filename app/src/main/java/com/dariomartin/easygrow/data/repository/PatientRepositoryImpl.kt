package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.BodyPart
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.dariomartin.easygrow.presentation.patient.profile.PatientForm
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor() : IPatientRepository {

    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirestoreDataSource()

    override suspend fun getPatient(patientId: String?): Patient? {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getPatient(uid)?.let { dto ->
                Mapper.patientDtoMapper(dto)
            }
        }
    }

    override suspend fun recordAdministration(newBodyPart: BodyPart, date: Calendar) {

    }

    override suspend fun updatePatient(patientId: String, patientForm: PatientForm) {
        getPatient(patientId)?.apply {
            this.name = patientForm.name ?: ""
            this.surname = patientForm.surname ?: ""
            this.height = patientForm.height
            this.weight = patientForm.weight
            this.birthday = patientForm.birthday
        }?.let {
            firestore.updatePatient(Mapper.patientMapper(it))
        }
    }

    override fun getAdministrations(patientId: String?): LiveData<List<Administration>> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getAdministrations(uid).map { list ->
                list.map { dto -> Mapper.administrationDtoMapper(dto) }
            }
        } ?: MutableLiveData()
    }

    override suspend fun addAdministration(administration: Administration) {
        auth.currentUser?.uid?.let {
            firestore.addAdministration(
                it,
                Mapper.administrationMapper(administration)
            )
        }
    }

    override fun allPatients(): LiveData<List<Patient>> {
        return firestore.getAllPatients().map { list ->
            list.map { Mapper.patientDtoMapper(it) }
        }
    }

    override fun getLivePatient(patientId: String?): LiveData<Patient> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getLivePatient(uid).map { Mapper.patientDtoMapper(it) }
        } ?: MutableLiveData()
    }


}