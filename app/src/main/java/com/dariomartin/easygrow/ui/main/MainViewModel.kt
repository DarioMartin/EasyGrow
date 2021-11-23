package com.dariomartin.easygrow.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.data.repository.IAuthRepository
import com.dariomartin.easygrow.data.repository.IUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val loginRepository: IAuthRepository,
    private val userRepository: IUserRepository
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
        viewModelScope.launch { userRepository.setType(type) }
    }

    fun logout() {
        loginRepository.logout()
    }
}