package com.dariomartin.easygrow.data.sources

import com.dariomartin.easygrow.data.Result

interface IAuth {
    suspend fun login(email: String, password: String): Result<Boolean>
    fun logout()
    fun getUserId(): String?
    suspend fun signUp(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Boolean>
}
