package com.dariomartin.easygrow.data.sources.firestore

import androidx.lifecycle.*
import com.dariomartin.easygrow.data.dto.*
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
        private const val PENS = "pens"
        private const val USED_PENS = "used_pens"

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
        firestore.collection(DRUGS).document(drug.id).set(drug).await()
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

    override suspend fun addAdministration(patientId: String, administration: AdministrationDTO) {
        firestore.collection(PATIENTS).document(patientId).collection(ADMINISTRATIONS)
            .add(administration).await()
    }

    override suspend fun assignPatientToDoctor(patientId: String, doctorId: String) {
        firestore.collection(DOCTORS).document(doctorId)
            .update(PATIENTS, FieldValue.arrayUnion(patientId)).await()
    }

    override suspend fun removePatientFromDoctor(patientId: String, doctorId: String) {
        firestore.collection(DOCTORS).document(doctorId)
            .update(PATIENTS, FieldValue.arrayRemove(patientId)).await()
    }

    override fun getDoctorPatients(doctorId: String): LiveData<List<PatientDTO>> {
        val result = MediatorLiveData<List<PatientDTO>>()

        var doctorPatients: List<String>? = null
        var allPatients: List<PatientDTO>? = null

        result.addSource(getLiveDoctor(doctorId)) { doctor ->
            doctorPatients = doctor.patients

            if (doctorPatients.isNullOrEmpty()) {
                result.postValue(listOf())
            } else if (allPatients != null) {
                result.postValue(allPatients?.filter { it.id in (doctorPatients ?: listOf()) })
            }
        }

        result.addSource(getAllPatients()) { source ->
            allPatients = source

            if (allPatients.isNullOrEmpty()) {
                result.postValue(listOf())
            } else if (doctorPatients != null) {
                result.postValue(allPatients?.filter { it.id in (doctorPatients ?: listOf()) })
            }
        }

        return result
    }

    override fun getNotAssignedPatients(doctorId: String): LiveData<List<PatientDTO>> {
        val result = MediatorLiveData<List<PatientDTO>>()

        var all: MutableList<PatientDTO>? = null
        var assigned: MutableList<PatientDTO>? = null

        result.addSource(getDoctorPatients(doctorId)) { assignedSource ->
            assigned = assignedSource.toMutableList()
            all?.let {
                it.removeIf { p -> p.id in assignedSource.map { a -> a.id } }
                result.postValue(it)
            }
        }

        result.addSource(getAllPatients()) { allSource ->
            val allSourceMut = allSource.toMutableList()
            all = allSourceMut
            assigned?.let {
                allSourceMut.removeIf { p -> p.id in it.map { a -> a.id } }
                result.postValue(allSourceMut)
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

    override fun getAdministrations(patientId: String): LiveData<List<AdministrationDTO>> {

        val liveData = MutableLiveData<List<AdministrationDTO>>()

        firestore.collection(PATIENTS)
            .document(patientId)
            .collection(ADMINISTRATIONS).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    liveData.postValue(
                        snapshot.documents.mapNotNull { doc ->
                            doc.toObject(AdministrationDTO::class.java)
                                ?.apply { id = doc.id }
                        }
                    )
                } else {
                    liveData.postValue(listOf())
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
            } else {
                liveData.postValue(listOf())
            }
        }

        return liveData
    }

    override fun getLiveDrug(drugId: String): LiveData<DrugDTO> {
        val liveData = MutableLiveData<DrugDTO>()

        firestore.collection(DRUGS).document(drugId).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                liveData.postValue(
                    snapshot.toObject(DrugDTO::class.java)
                        ?.apply { id = snapshot.id }
                )
            }
        }

        return liveData
    }

    override fun getDrugs(): LiveData<List<DrugDTO>> {
        val liveData = MutableLiveData<List<DrugDTO>>()

        firestore.collection(DRUGS).addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                liveData.postValue(
                    snapshot.documents.mapNotNull { doc ->
                        doc.toObject(DrugDTO::class.java)
                            ?.apply { id = doc.id }
                    }
                )
            } else {
                liveData.postValue(listOf())
            }
        }

        return liveData
    }

    override suspend fun addPen(patientId: String, pen: PenDTO) {
        firestore.collection(PATIENTS).document(patientId).collection(PENS).add(pen).await()
    }

    override suspend fun removePen(patientId: String, pen: PenDTO) {
        firestore.collection(PATIENTS).document(patientId).collection(PENS).document(pen.id)
            .delete()
            .await()
        firestore.collection(PATIENTS).document(patientId).collection(USED_PENS).add(pen)
            .await()
    }

    override suspend fun getPen(patientId: String, penId: String): PenDTO? {
        val result =
            firestore.collection(PATIENTS).document(patientId).collection(PENS).document(penId)
                .get().await()
        return result.toObject(PenDTO::class.java)?.apply { id = result.id }
    }

    override suspend fun updatePen(patientId: String, pen: PenDTO) {
        firestore.collection(PATIENTS).document(patientId).collection(PENS).document(pen.id)
            .set(pen).await()
    }

    override fun getPens(patientId: String): LiveData<List<PenDTO>> {
        val liveData = MutableLiveData<List<PenDTO>>()

        firestore.collection(PATIENTS).document(patientId).collection(PENS)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    liveData.postValue(
                        snapshot.documents.mapNotNull { doc ->
                            doc.toObject(PenDTO::class.java)
                                ?.apply { id = doc.id }
                        }
                    )
                } else {
                    liveData.postValue(listOf())
                }
            }

        return liveData
    }

    override fun removePens(patientId: String) {
        val collection = firestore.collection(PATIENTS).document(patientId).collection(PENS)
        val batchSize = 20L
        try {
            // Retrieve a small batch of documents to avoid out-of-memory errors/
            var deleted = 0
            collection
                .limit(batchSize)
                .get()
                .addOnCompleteListener {
                    for (document in it.result?.documents ?: listOf()) {
                        document.reference.delete()
                        ++deleted
                    }
                    if (deleted >= batchSize) {
                        // retrieve and delete another batch
                        removePens(patientId)
                    }
                }
        } catch (e: Exception) {
            System.err.println("Error deleting collection : " + e.message)
        }
    }

    override fun getUsedPens(patientId: String): LiveData<List<PenDTO>> {
        val liveData = MutableLiveData<List<PenDTO>>()

        firestore.collection(PATIENTS).document(patientId).collection(USED_PENS)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && !snapshot.isEmpty) {
                    liveData.postValue(
                        snapshot.documents.mapNotNull { doc ->
                            doc.toObject(PenDTO::class.java)
                                ?.apply { id = doc.id }
                        }
                    )
                } else {
                    liveData.postValue(listOf())
                }
            }

        return liveData
    }

}