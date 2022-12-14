package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Administration
import com.dariomartin.easygrow.data.model.HeightMeasure
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.Pen
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class PatientRepositoryImpl @Inject constructor() : IPatientRepository {

    private val auth = FirebaseAuth.getInstance()

    private val firestore = FirestoreDataSource()

    override suspend fun updatePatient(patientId: String, patient: Patient) {
        firestore.updatePatient(Mapper.patientMapper(patient))
    }

    override fun getAdministrations(patientId: String?): LiveData<List<Administration>> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getAdministrations(uid).map { list ->
                list.map { dto -> Mapper.administrationDtoMapper(dto) }
            }
        } ?: MutableLiveData(listOf())
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

        val result = MediatorLiveData<Patient>()

        var patient: Patient? = null
        var pens: MutableList<Pen> = mutableListOf()

        val patientSource = (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getPatient(uid).map { Mapper.patientDtoMapper(it) }
        } ?: MutableLiveData()

        val pensSource = getPens(patientId)

        result.addSource(patientSource) { p ->
            patient = p
            patient?.apply {
                treatment?.pens = pens
            }

            patient?.let { result.postValue(it) }
        }
        result.addSource(pensSource) { p ->
            pens = p.toMutableList()
            patient?.apply {
                treatment?.pens = pens
            }
            patient?.let { result.postValue(it) }
        }

        return result
    }

    override suspend fun addPen(patientId: String, newPen: Pen) {
        firestore.addPen(patientId, Mapper.penMapper(newPen))
    }

    override fun getPens(patientId: String?): LiveData<List<Pen>> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getPens(uid).map { list ->
                list.map { Mapper.penDtoMapper(it) }
            }
        } ?: MutableLiveData()
    }

    override fun removePens(patientId: String) {
        firestore.removePens(patientId)
    }

    override suspend fun updatePen(patientId: String?, pen: Pen) {
        (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.updatePen(uid, Mapper.penMapper(pen))
        }
    }

    override suspend fun removePen(patientId: String, pen: Pen) {
        firestore.removePen(patientId, Mapper.penMapper(pen))
    }

    override fun getUsedPens(patientId: String?): LiveData<List<Pen>> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getUsedPens(uid).map { list ->
                list.map { dto -> Mapper.penDtoMapper(dto) }
            }
        } ?: MutableLiveData(listOf())
    }

    override suspend fun addHeightMeasure(patientId: String, heightMeasure: HeightMeasure) {
        firestore.addHeightMeasure(patientId, Mapper.heightMeasureMapper(heightMeasure))
    }

    override fun getHeightMeasures(patientId: String?): LiveData<List<HeightMeasure>> {
        return (patientId ?: auth.currentUser?.uid)?.let { uid ->
            firestore.getPatientHeightMeasures(uid).map { list ->
                list.map { Mapper.heightMeasureDtoMapper(it) }
            }
        } ?: MutableLiveData()
    }

}