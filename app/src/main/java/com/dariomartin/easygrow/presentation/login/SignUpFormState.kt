package com.dariomartin.easygrow.presentation.login

data class SignUpFormState(
    val nameError: Int? = null,
    val surnameError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)