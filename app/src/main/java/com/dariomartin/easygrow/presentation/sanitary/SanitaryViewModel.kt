package com.dariomartin.easygrow.presentation.sanitary

import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SanitaryViewModel @Inject constructor(private val loginRepository: IAuthRepository) :
    ViewModel() {

    fun logout() {
        loginRepository.logout()
    }

}