package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.Result
import com.dariomartin.easygrow.data.sources.IAuth
import com.dariomartin.easygrow.data.sources.firestore.FirebaseAuthSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : IAuthRepository {

    private val auth: IAuth = FirebaseAuthSource()

    override fun isLoggedIn() = auth.getUserId() != null

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return auth.login(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<Boolean> {
        return auth.signUp(email, password)
    }

    override fun logout() {
        auth.logout()
    }

}