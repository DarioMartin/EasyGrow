package com.dariomartin.easygrow.presentation.patient.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor() : ViewModel() {

    val generalStatistics by lazy {
        val liveData = MutableLiveData<GeneralStatistics>()
        viewModelScope.launch {
            liveData.value = GeneralStatistics()
        }
        return@lazy liveData
    }

    val heightStatistics by lazy {
        val liveData = MutableLiveData<CurrentHeightData>()
        viewModelScope.launch {
            liveData.value = CurrentHeightData()
        }
        return@lazy liveData
    }

}