package com.dariomartin.easygrow.presentation.sanitary.createdrug

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dariomartin.easygrow.data.repository.IDrugRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDrugViewModel @Inject constructor(private val drugRepository: IDrugRepository) :
    ViewModel() {

    val successfulSaving: MutableLiveData<Boolean> = MutableLiveData(false)

    fun saveDrug(form: DrugForm) {
        viewModelScope.launch {
            drugRepository.updateDrug(form)
            successfulSaving.postValue(true)
        }
    }

}