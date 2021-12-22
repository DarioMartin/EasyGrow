package com.dariomartin.easygrow.presentation.patient.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.databinding.FragmentStatisticsBinding
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : BaseFragment<FragmentStatisticsBinding, StatisticsViewModel>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHeightStatistics().observe(viewLifecycleOwner) { currentHeightData ->
            if (currentHeightData != null) {
                binding.heightStats.updateCurrentHeightData(currentHeightData)
            }
        }

        viewModel.getGeneralStatistics().observe(viewLifecycleOwner) { generalStatistics ->
            if (generalStatistics != null) {
                binding.generalStats.updateGeneralStatistics(generalStatistics)
            }
        }

        viewModel.heightMeasures.observe(viewLifecycleOwner) { heightMeasures ->
            if (heightMeasures != null) {
                binding.chartStats.updateChartData(heightMeasures)
            }
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStatisticsBinding {
        return FragmentStatisticsBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): StatisticsViewModel {
        return ViewModelProvider(this)[StatisticsViewModel::class.java]
    }

}