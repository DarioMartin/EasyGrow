package com.dariomartin.easygrow.data.repository

import com.dariomartin.easygrow.data.model.User

interface IUserRepository {
    suspend fun getType(): User.Type?
    suspend fun setType(type: User.Type)
}