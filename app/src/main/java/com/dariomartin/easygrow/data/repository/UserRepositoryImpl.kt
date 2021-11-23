package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.sources.IAuth
import com.dariomartin.easygrow.data.sources.IUserDataSource
import com.dariomartin.easygrow.data.sources.firestore.FirebaseAuthSource
import com.dariomartin.easygrow.data.sources.firestore.FirestoreUserDataSource
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : IUserRepository {

    private val auth: IAuth = FirebaseAuthSource()
    private val userDataSource: IUserDataSource = FirestoreUserDataSource()

    override suspend fun getType(): User.Type? {
        val userId = auth.getUserId() ?: return null
        return userDataSource.getType(userId)
    }

    override suspend fun setType(type: User.Type) {
        val userId = auth.getUserId()
        userId?.let { userDataSource.setType(it, type) }
    }
}