package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.model.User

interface IUserRepository {
    suspend fun getType(): User.Type?
    suspend fun updateUser(name: String, surname: String, type: User.Type)
}