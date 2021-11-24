package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DoctorRepositoryImpl @Inject constructor() : IDoctorRepository {
    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirestoreDataSource()

    override suspend fun getAssignedPatients(): List<Patient> {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getDoctorPatients(uid).map { dto ->
                Mapper.patientDtoMapper(dto)
            }
        } ?: listOf()
    }

    override suspend fun getPatient(): Patient? {
        return auth.currentUser?.uid?.let { uid ->
            firestore.getPatient(uid)?.let { dto ->
                Mapper.patientDtoMapper(dto)
            }
        }
    }

    override suspend fun getDrugs(): List<Drug> {
        return firestore.getDrugs().map { Mapper.drugDtoMapper(it) }
    }

}