package com.dariomartin.easygrow.data.sources.firestore

import androidx.lifecycle.*
import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.DoctorDTO
import com.dariomartin.easygrow.data.dto.DrugDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IDataSource
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirestoreDataSource : IDataSource {

    companion object {
        private const val USERS = "users"
        private const val PATIENTS = "patients"
        private const val DOCTORS = "doctors"
        private const val DRUGS = "drugs"
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

    override suspend fun addPatient(patient: PatientDTO) {
        firestore.collection(PATIENTS).document(patient.id).set(patient).await()
    }

    override suspend fun removePatient(patientId: String) {
        firestore.collection(PATIENTS).document(patientId).delete().await()
    }

    override suspend fun getPatient(patientId: String): PatientDTO? {
        val result = firestore.collection(PATIENTS).document(patientId).get().await()
        return result.toObject(PatientDTO::class.java)?.apply { id = result.id }
    }

    override suspend fun updatePatient(patient: PatientDTO) {
        addPatient(patient)
    }

    override suspend fun addDoctor(doctor: DoctorDTO) {
        firestore.collection(DOCTORS).document(doctor.id).set(doctor).await()
    }

    override suspend fun removeDoctor(doctorId: String) {
        firestore.collection(DRUGS).document(doctorId).delete().await()
    }

    override suspend fun getDoctor(doctorId: String): DoctorDTO? {
        val result = firestore.collection(DOCTORS).document(doctorId).get().await()
        return result.toObject(DoctorDTO::class.java)?.apply { id = result.id }
    }

    override suspend fun updateDoctor(doctor: DoctorDTO) {
        addDoctor(doctor)
    }

    override suspend fun addDrug(drug: DrugDTO) {
        firestore.collection(DRUGS).add(drug).await()
    }

    override suspend fun removeDrug(drugId: String) {
        firestore.collection(DRUGS).document(drugId).delete().await()
    }

    override suspend fun getDrug(drugId: String): DrugDTO? {
        val result = firestore.collection(DRUGS).document(drugId).get().await()
        return result.toObject(DrugDTO::class.java)?.apply { id = result.id }
    }

    override suspend fun updateDrug(drug: DrugDTO) {
        addDrug(drug)
    }

    override suspend fun getDrugs(): List<DrugDTO> {
        return firestore.collection(DRUGS).get().await().toObjects(DrugDTO::class.java)
    }


    override suspend fun addAdministration(patientId: String, administration: AdministrationDTO) {
        firestore.collection(PATIENTS).document(patientId).collection(ADMINISTRATIONS)
            .add(administration).await()
    }

    override suspend fun getAdministrations(patientId: String): List<AdministrationDTO> {
        val result = firestore.collection(PATIENTS)
            .document(patientId)
            .collection(ADMINISTRATIONS)
            .get().await()

        return result.documents.map { doc ->
            doc.toObject(AdministrationDTO::class.java)?.apply { id = doc.id }
                ?: AdministrationDTO()
        }
    }

    override suspend fun assignPatientToDoctor(patientId: String, doctorId: String) {
        firestore.collection(DOCTORS).document(doctorId)
            .update(PATIENTS, FieldValue.arrayUnion(patientId)).await()
    }

    override suspend fun removePatientFromDoctor(patientId: String, doctorId: String) {
        firestore.collection(DOCTORS).document(doctorId)
            .update(PATIENTS, FieldValue.arrayRemove(patientId)).await()
    }


    override fun getDoctorPatients(doctorId: String): LiveData<MutableList<PatientDTO>> {
        val result = MediatorLiveData<MutableList<PatientDTO>>()

        val doctorLiveData = getLiveDoctor(doctorId).map { doctorDto ->
            doctorDto.patients
        }

        result.addSource(doctorLiveData) { patients ->
            val list = mutableListOf<PatientDTO>()
            patients.map {
                result.addSource(getLivePatient(it)) { patientDTO ->
                    list.add(patientDTO)
                    result.value = list
                }
            }
        }

        return result
    }


    override fun getLivePatient(patientId: String): LiveData<PatientDTO> {
        val liveData = MutableLiveData<PatientDTO>()

        firestore.collection(PATIENTS).document(patientId).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                liveData.postValue(
                    snapshot.toObject(PatientDTO::class.java)
                        ?.apply { id = snapshot.id }
                )
            }
        }

        return liveData
    }

    override fun getLiveDoctor(doctorId: String): LiveData<DoctorDTO> {
        val liveData = MutableLiveData<DoctorDTO>()

        firestore.collection(DOCTORS).document(doctorId).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                liveData.postValue(
                    snapshot.toObject(DoctorDTO::class.java)
                        ?.apply { id = snapshot.id }
                )
            }
        }

        return liveData
    }

    override fun getAllPatients(): LiveData<List<PatientDTO>> {
        val liveData = MutableLiveData<List<PatientDTO>>()

        firestore.collection(PATIENTS).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                liveData.postValue(
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(PatientDTO::class.java)
                            ?.apply { id = doc.id }
                    }
                )
            }
        }

        return liveData
    }
}