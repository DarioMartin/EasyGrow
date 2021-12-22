package com.dariomartin.easygrow.presentation.patient.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val heightMeasures = patientRepository.getHeightMeasures()


    private var mGeneralStatistics = GeneralStatistics()
    private var mHeightStatistics = HeightStatistics()

    init {
        getGeneralStatistics()
    }

    fun getGeneralStatistics(): LiveData<GeneralStatistics> {
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

            generalStatistics.addSource(heightMeasures) { list ->
                if (list.size > 1) {
                    val firstMeasure: Int = list.first().height
                    val lastMeasure: Int = list.last().height

                    mGeneralStatistics.totalGrowth = lastMeasure - firstMeasure

                    generalStatistics.postValue(mGeneralStatistics)
                }
            }
        }
        return generalStatistics
    }

    fun getHeightStatistics(): LiveData<HeightStatistics> {
        val heightStatistics = MediatorLiveData<HeightStatistics>()

        heightStatistics.addSource(heightMeasures) { list ->
            val oneMonthAgo = Calendar.getInstance()
            oneMonthAgo.add(Calendar.MONTH, -1)
            val first = list.firstOrNull { it.date != null && it.date!! > oneMonthAgo }

            if (first != null) {
                mHeightStatistics.last30Days = list.last().height - first.height
            }

            mHeightStatistics.height = list.last().height

            heightStatistics.postValue(mHeightStatistics)
        }

        return heightStatistics
    }


}