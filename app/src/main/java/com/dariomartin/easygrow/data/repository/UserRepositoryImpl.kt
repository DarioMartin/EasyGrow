package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.dto.DoctorDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IAuth
import com.dariomartin.easygrow.data.sources.IDataSource
import com.dariomartin.easygrow.data.sources.firestore.FirebaseAuthSource
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : IUserRepository {

    private val auth: IAuth = FirebaseAuthSource()
    private val userDataSource: IDataSource = FirestoreDataSource()

    override suspend fun getType(): User.Type? {
        val userId = auth.getUserId() ?: return null
        return userDataSource.getType(userId)
    }

    override suspend fun setType(type: User.Type) {
        val userId = auth.getUserId()
        userId?.let { userDataSource.setType(it, type) }

        when (type) {
            User.Type.PATIENT -> auth.getUserId()
                ?.let { userDataSource.addPatient(PatientDTO(id = it)) }
            User.Type.SANITARY -> auth.getUserId()
                ?.let { userDataSource.addDoctor(DoctorDTO(id = it)) }
        }
    }
}