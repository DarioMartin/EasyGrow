package com.dariomartin.easygrow.ui.login

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.Result
import com.dariomartin.easygrow.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: IAuthRepository) :
    ViewModel() {

    private val _signUpForm = MutableLiveData<LoginFormState>()
    val signUpFormState: LiveData<LoginFormState> = _signUpForm

    private val _signUpResult = MutableLiveData<LoginResult>()
    val signUpResult: LiveData<LoginResult> = _signUpResult

    init {
        if (loginRepository.isLoggedIn()) {
            _signUpResult.value = LoginResult(success = true)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.login(email, password)

            if (result is Result.Success) {
                _signUpResult.postValue(
                    LoginResult(success = true)
                )
            } else {
                _signUpResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }

    fun signUp(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch {
            val result = loginRepository.signUp(name, surname, email, password)

            if (result is Result.Success) {
                _signUpResult.postValue(
                    LoginResult(success = true)
                )
            } else {
                _signUpResult.postValue(LoginResult(error = R.string.login_failed))
            }
        }
    }

    fun loginDataChanged(email: String, password: String) {
        if (!isUserNameValid(email)) {
            _signUpForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _signUpForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun signUpDataChanged(name: String, surname: String, email: String, password: String) {
        if (!isUserNameValid(email)) {

        } else if (!isUserNameValid(surname)) {

        } else if (!isUserNameValid(email)) {
            _signUpForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _signUpForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _signUpForm.value = LoginFormState(isDataValid = true)
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