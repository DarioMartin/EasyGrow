package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor() : IDoctorRepository {
    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirestoreDataSource()

    override fun getAssignedPatients(): LiveData<List<Patient>> {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getDoctorPatients(uid).map { list ->
                list.map { item -> Mapper.patientDtoMapper(item) }.toMutableList()
            }
        } ?: MutableLiveData(listOf())
    }

    override suspend fun removePatientFromDoctor(patientId: String) {
        auth.currentUser?.uid?.let { uid ->
            firestore.removePatientFromDoctor(patientId, uid)
        }
    }

    override suspend fun assignPatient(patientId: String) {
        auth.currentUser?.uid?.let { uid ->
            firestore.assignPatientToDoctor(patientId, uid)
        }
    }

    override fun getNotAssignedPatients(): LiveData<List<Patient>> {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getNotAssignedPatients(uid).map { list ->
                list.map { item -> Mapper.patientDtoMapper(item) }
            }
        } ?: MutableLiveData(listOf())
    }

}