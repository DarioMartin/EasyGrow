package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.Result

interface IAuthRepository {
    suspend fun login(email: String, password: String): Result<Boolean>
    fun logout()
    fun isLoggedIn(): Boolean
    suspend fun signUp(email: String, password: String): Result<Boolean>
}