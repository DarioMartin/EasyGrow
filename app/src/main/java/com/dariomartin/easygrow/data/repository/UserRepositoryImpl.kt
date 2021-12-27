package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.dto.DoctorDTO
import com.dariomartin.easygrow.data.dto.PatientDTO
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IAuth
import com.dariomartin.easygrow.data.sources.IDataSource
import com.dariomartin.easygrow.data.sources.firestore.FirebaseAuthSource
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.dariomartin.easygrow.injecton.EGPreferences
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val pref: EGPreferences) :
    IUserRepository {

    private val auth: IAuth = FirebaseAuthSource()
    private val userDataSource: IDataSource = FirestoreDataSource()

    override suspend fun getType(): User.Type? {
        var type = pref.getUserType()
        if (type == null) {
            val userId = auth.getUserId() ?: return null
            type = userDataSource.getType(userId)
            pref.saveUserType(type)
        }
        return type
    }

    override suspend fun updateUser(name: String, surname: String, type: User.Type) {
        val userId = auth.getUserId()
        userId?.let { userDataSource.setType(it, type) }

        when (type) {
            User.Type.PATIENT -> auth.getUserId()
                ?.let {
                    userDataSource.addPatient(
                        PatientDTO(
                            id = it,
                            name = name,
                            surname = surname
                        )
                    )
                }
            User.Type.SANITARY -> auth.getUserId()
                ?.let {
                    userDataSource.addDoctor(
                        DoctorDTO(
                            id = it,
                            name = name,
                            surname = surname
                        )
                    )
                }
        }
    }
}