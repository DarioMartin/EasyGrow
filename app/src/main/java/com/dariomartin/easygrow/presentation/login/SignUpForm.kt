package com.dariomartin.easygrow.presentation.login

data class SignUpForm(
    var name: String? = null,
    var surname: String? = null,
    var email: String? = null,
    var password: String? = null
) {

    fun isValid() = isValidName() && isValidSurname() && isValidEmail() && isValidPassword()

    fun isValidName(): Boolean {
        return !name.isNullOrEmpty()
    }

    fun isValidSurname(): Boolean {
        return !surname.isNullOrEmpty()
    }

    fun isValidEmail(): Boolean {
        return !email.isNullOrEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }

    fun isValidPassword(): Boolean {
        return !password.isNullOrEmpty() && password?.length!! > 7
    }
}