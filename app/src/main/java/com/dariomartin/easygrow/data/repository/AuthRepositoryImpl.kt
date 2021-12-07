package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.Result
import com.dariomartin.easygrow.data.sources.IAuth
import com.dariomartin.easygrow.data.sources.firestore.FirebaseAuthSource
import com.dariomartin.easygrow.injecton.EGPreferences
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val pref: EGPreferences) : IAuthRepository {

    private val auth: IAuth = FirebaseAuthSource()

    override fun isLoggedIn() = auth.getUserId() != null

    override suspend fun login(email: String, password: String): Result<Boolean> {
        return auth.login(email, password)
    }

    override suspend fun signUp(email: String, password: String): Result<Boolean> {
        return auth.signUp(email, password)
    }

    override fun logout() {
        pref.saveUserType(null)
        auth.logout()
    }

}