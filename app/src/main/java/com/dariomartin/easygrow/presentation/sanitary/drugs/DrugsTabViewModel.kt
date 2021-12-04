package com.dariomartin.easygrow.presentation.sanitary.drugs

import androidx.lifecycle.*
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.repository.IDoctorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrugsTabViewModel @Inject constructor(private val doctorRepository: IDoctorRepository) :
    ViewModel() {

    val drugs by lazy {
        val liveData = MutableLiveData<List<Drug>>()
        viewModelScope.launch {
            liveData.postValue(doctorRepository.getDrugs())
        }
        return@lazy liveData
    }


}