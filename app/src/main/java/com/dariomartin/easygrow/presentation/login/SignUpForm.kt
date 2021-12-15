package com.dariomartin.easygrow.presentation.login

data class SignUpForm(
    var name: String? = null,
    var surname: String? = null,
    var email: String? = null,
    var password: String? = null
) {

    fun isValid() = isValidName() && isValidSurname() && isValidEmail() && isValidPassword()

    private fun isValidName(): Boolean {
        return !name.isNullOrEmpty()
    }

    private fun isValidSurname(): Boolean {
        return !surname.isNullOrEmpty()
    }

    fun isValidEmail(): Boolean {
        return !email.isNullOrEmpty()
    }

    fun isValidPassword(): Boolean {
        return !password.isNullOrEmpty()
    }
}