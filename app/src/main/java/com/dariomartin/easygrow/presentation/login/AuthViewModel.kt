package com.dariomartin.easygrow.presentation.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.Result
import com.dariomartin.easygrow.data.repository.IAuthRepository
import com.dariomartin.easygrow.injecton.EGPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: IAuthRepository,
    private val pref: EGPreferences
) :
    ViewModel() {

    private val _loginResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _loginResult

    private val _signUpResult = MutableLiveData<AuthResult>()
    val signUpResult: LiveData<AuthResult> = _signUpResult

    init {
        if (authRepository.isLoggedIn()) {
            _loginResult.value = AuthResult(success = true)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.login(email, password)

            if (result is Result.Success) {
                _loginResult.postValue(
                    AuthResult(success = true)
                )
            } else {
                _loginResult.postValue(AuthResult(error = R.string.login_failed))
            }
        }
    }

    fun signUp(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signUp(email, password)

            if (result is Result.Success) {
                pref.saveUserName(name)
                pref.saveUserSurname(surname)
                _signUpResult.postValue(
                    AuthResult(success = true)
                )
            } else {
                _signUpResult.postValue(AuthResult(error = R.string.sign_up_failed))
            }
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

}