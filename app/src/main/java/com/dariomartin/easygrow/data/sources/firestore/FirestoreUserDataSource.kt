package com.dariomartin.easygrow.data.sources.firestore

import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IUserDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreUserDataSource : IUserDataSource {

    companion object {
        private const val USERS = "users"
        private const val PATIENTS = "patients"
        private const val DOCTORS = "doctors"
        private const val ADMINISTRATIONS = "administrations"

    }

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun getType(userId: String): User.Type? {
        val result = firestore.collection(USERS).document(userId).get().await()
        return result?.getString("type")?.let {
            User.Type.valueOf(it)
        }
    }

    override suspend fun setType(userId: String, type: User.Type) {
        val data = hashMapOf(
            "type" to type
        )
        firestore.collection(USERS).document(userId).set(data)
    }

    override suspend fun getPatient(userId: String): PatientDTO? {
        val result = firestore.collection(PATIENTS).document(userId).get().await()
        return result.toObject(PatientDTO::class.java)
    }

    override suspend fun updatePatient(patientId: String, patient: PatientDTO) {
        firestore.collection(PATIENTS).document(patientId).set(patient)
    }

    override suspend fun getAdministrations(patientId: String): List<AdministrationDTO> {
        val result = firestore.collection(PATIENTS)
            .document(patientId)
            .collection(ADMINISTRATIONS)
            .get().await()

        return result.toObjects(AdministrationDTO::class.java)
    }

    override suspend fun addAdminstration(patientId: String, administrationDTO: AdministrationDTO) {
        firestore.collection(PATIENTS)
            .document(patientId)
            .collection(ADMINISTRATIONS)
            .add(administrationDTO).await()
    }
}