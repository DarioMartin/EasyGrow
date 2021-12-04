package com.dariomartin.easygrow.presentation.login

data class AuthResult(
    val success: Boolean = false,
    val error: Int? = null
)