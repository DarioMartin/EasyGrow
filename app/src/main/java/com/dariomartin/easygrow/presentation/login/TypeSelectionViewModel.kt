package com.dariomartin.easygrow.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.repository.IAuthRepository
import com.dariomartin.easygrow.data.repository.IUserRepository
import com.dariomartin.easygrow.injecton.EGPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TypeSelectionViewModel @Inject constructor(
    private val loginRepository: IAuthRepository,
    private val userRepository: IUserRepository,
    private val pref: EGPreferences
) :
    ViewModel() {

    val userType by lazy {
        val liveData = MutableLiveData<User.Type>()
        viewModelScope.launch {
            liveData.value = userRepository.getType()
        }
        return@lazy liveData
    }

    fun setUserType(type: User.Type) {
        GlobalScope.launch {
            userRepository.updateUser(pref.getUserName(), pref.getUserSurname(), type)
            userType.postValue(type)
        }
    }

    fun logout() {
        loginRepository.logout()
    }
}