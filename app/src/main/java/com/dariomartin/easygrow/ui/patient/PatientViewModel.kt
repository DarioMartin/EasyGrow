package com.dariomartin.easygrow.ui.patient

import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PatientViewModel @Inject constructor(private val loginRepository: IAuthRepository) :
    ViewModel() {

    fun logout() {
        loginRepository.logout()
    }

}