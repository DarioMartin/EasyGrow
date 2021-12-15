package com.dariomartin.easygrow.presentation.login

data class LoginForm(
    var email: String? = null,
    var password: String? = null
) {

    fun isValid() = isValidEmail() && isValidPassword()

    fun isValidEmail(): Boolean {
        return !email.isNullOrEmpty() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(email!!).matches()
    }

    fun isValidPassword(): Boolean {
        return !password.isNullOrEmpty() && password?.length!! > 7
    }
}