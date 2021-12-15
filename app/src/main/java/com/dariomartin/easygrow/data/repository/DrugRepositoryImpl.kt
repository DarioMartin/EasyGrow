package com.dariomartin.easygrow.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.dariomartin.easygrow.data.mapper.Mapper
import com.dariomartin.easygrow.data.model.Concentration
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.data.sources.firestore.FirestoreDataSource
import com.dariomartin.easygrow.presentation.sanitary.createdrug.DrugForm
import javax.inject.Inject

class DrugRepositoryImpl @Inject constructor() : IDrugRepository {

    private val firestore = FirestoreDataSource()

    override suspend fun getDrug(drugId: String): Drug? {
        return firestore.getDrug(drugId)?.let { dto ->
            Mapper.drugDtoMapper(dto)
        }
    }

    override fun getLiveDrug(drugId: String): LiveData<Drug> {
        return firestore.getLiveDrug(drugId).map { Mapper.drugDtoMapper(it) }
    }

    override suspend fun updateDrug(drugForm: DrugForm) {
        val drugDTO = Mapper.drugMapper(
            Drug(
                name = drugForm.name ?: "",
                pharmacy = drugForm.pharmacy ?: "",
                url = drugForm.url ?: "",
                concentration = Concentration(
                    mass = drugForm.drugMass,
                    volume = drugForm.drugVolume
                ),
                cartridgeVolume = drugForm.cartridgeVolume
            )
        )
        firestore.updateDrug(drugDTO)
    }

    override suspend fun removeDrug(drugId: String) {
        firestore.removeDrug(drugId)
    }

    override fun getDrugs(): LiveData<List<Drug>> {
        return firestore.getDrugs().map { list ->
            list.map { Mapper.drugDtoMapper(it) }
        }
    }
}