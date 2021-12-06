package com.dariomartin.easygrow.data.sources.mock

import android.icu.util.Measure
import android.icu.util.MeasureUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dariomartin.easygrow.data.dto.AdministrationDTO
import com.dariomartin.easygrow.data.dto.DoctorDTO
import com.dariomartin.easygrow.data.dto.DrugDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.model.Patient
import com.dariomartin.easygrow.data.model.Treatment
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IDataSource
import java.util.*
import javax.inject.Inject

class PatientMockDataSource @Inject constructor() : IDataSource {

    lateinit var patient: Patient

    init {
        val patientWeight = 30f
        patient = Patient(
            id = "1",
            type = User.Type.PATIENT,
            email = "patient@email.com",
            name = "Neville",
            surname = "Griffin",
            photo = "https://image.shutterstock.com/image-photo/funny-small-child-denim-tshirt-260nw-1008710635.jpg",
            height = 130,
            birthday = getBirthDay(),
            weight = patientWeight,
            treatment = getTreatment(patientWeight)
        )
    }

    override suspend fun getType(userId: String): User.Type {
        return User.Type.PATIENT
    }

    override suspend fun setType(userId: String, type: User.Type) {
    }

    override suspend fun addPatient(patient: PatientDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun removePatient(patientId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getPatient(patientId: String): PatientDTO {
        return Mapper.patientMapper(patient)
    }

    override suspend fun addDoctor(doctor: DoctorDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun removeDoctor(doctorId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getDoctor(doctorId: String): DoctorDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun updateDoctor(doctor: DoctorDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun addDrug(drug: DrugDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun removeDrug(drugId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getDrug(drugId: String): DrugDTO? {
        TODO("Not yet implemented")
    }

    override suspend fun updateDrug(drug: DrugDTO) {
        TODO("Not yet implemented")
    }

    override fun getDrugs(): LiveData<List<DrugDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePatient(patient: PatientDTO) {
        this.patient.name = patient.name
        this.patient.surname = patient.surname
        this.patient.height = patient.height
        this.patient.weight = patient.weight
    }

    override  fun getAdministrations(patientId: String): LiveData<List<AdministrationDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun assignPatientToDoctor(patientId: String, doctorId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removePatientFromDoctor(patientId: String, doctorId: String) {
        TODO("Not yet implemented")
    }

    override fun getDoctorPatients(doctorId: String): LiveData<MutableList<PatientDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun addAdministration(
        patientId: String,
        administrationDTO: AdministrationDTO
    ) {

    }

    private fun getTreatment(patientWeight: Float): Treatment {
        val drug = Drug()


        val lastDate = Calendar.getInstance()
        lastDate.add(Calendar.DATE, -18)

        val treatment = Treatment(drug = drug, lastUpdate = lastDate)

        treatment.setDose(Measure(0.035f * patientWeight, MeasureUnit.MILLIGRAM))
        treatment.addPens(5)

        return treatment
    }

    private fun getBirthDay(): Calendar {
        val cal = Calendar.getInstance()
        cal.set(2015, 4, 3)
        return cal
    }


    override fun getLivePatient(patientId: String): LiveData<PatientDTO> {
        return MutableLiveData()
    }

    override fun getLiveDoctor(doctorId: String): LiveData<DoctorDTO> {
        TODO("Not yet implemented")
    }

    override fun getAllPatients(): LiveData<List<PatientDTO>> {
        TODO("Not yet implemented")
    }

    override fun getLiveDrug(drugId: String): LiveData<DrugDTO> {
        TODO("Not yet implemented")
    }
}