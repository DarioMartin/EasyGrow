package com.dariomartin.easygrow.presentation.patient.statistics

import androidx.lifecycle.*
import com.dariomartin.easygrow.data.repository.IPatientRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    patientRepository: IPatientRepository,
) : ViewModel() {

    private val usedPensSource = patientRepository.getUsedPens()
    private val administrations = patientRepository.getAdministrations()

    var mGeneralStatistics = GeneralStatistics()

    init {
        loadGeneralStatistics()
    }

    fun loadGeneralStatistics(): LiveData<GeneralStatistics> {
        val generalStatistics = MediatorLiveData<GeneralStatistics>()
        viewModelScope.launch {
            generalStatistics.addSource(usedPensSource) { list ->
                mGeneralStatistics.usedPens = list.size
                generalStatistics.postValue(mGeneralStatistics)
            }

            generalStatistics.addSource(administrations) { list ->
                if (list.isNotEmpty()) {
                    val times: List<Long> = list.map {
                        val calendar = Calendar.getInstance()
                        calendar.time = it.date.time
                        calendar.set(0, 0, 0)
                        calendar.timeInMillis
                    }

                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = times.average().toLong()
                    mGeneralStatistics.averageTimeHour = calendar.get(Calendar.HOUR_OF_DAY)
                    mGeneralStatistics.averageTimeMinute = calendar.get(Calendar.MINUTE)
                    generalStatistics.postValue(mGeneralStatistics)
                }
            }
        }
        return generalStatistics
    }

    val heightStatistics by lazy {
        val liveData = MutableLiveData<CurrentHeightData>()
        viewModelScope.launch {
            liveData.value = CurrentHeightData()
        }
        return@lazy liveData
    }

}