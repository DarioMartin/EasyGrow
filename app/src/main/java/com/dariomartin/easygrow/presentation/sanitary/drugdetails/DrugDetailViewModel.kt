package com.dariomartin.easygrow.presentation.sanitary.drugdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.repository.IDrugRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrugDetailViewModel @Inject constructor(private val drugRepository: IDrugRepository) :
    ViewModel() {

    fun getDrug(drugId: String): LiveData<Drug> {
        return drugRepository.getLiveDrug(drugId)
    }
}