package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.presentation.sanitary.createdrug.DrugForm

interface IDrugRepository {
    fun getLiveDrug(drugId: String): LiveData<Drug>
    suspend fun updateDrug(drugForm: DrugForm)
    suspend fun removeDrug(drugId: String)
    fun getDrugs(): LiveData<List<Drug>>
}