package com.dariomartin.easygrow.presentation.sanitary.druglist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.repository.IDrugRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DrugsTabViewModel @Inject constructor(private val drugRepository: IDrugRepository) :
    ViewModel() {

    fun getDrugs(): LiveData<List<Drug>> {
        return drugRepository.getDrugs()
    }

}