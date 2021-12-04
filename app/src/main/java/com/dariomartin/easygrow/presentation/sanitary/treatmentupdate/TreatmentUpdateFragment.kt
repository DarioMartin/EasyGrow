package com.dariomartin.easygrow.presentation.sanitary.treatmentupdate

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dariomartin.easygrow.R

class TreatmentUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = TreatmentUpdateFragment()
    }

    private lateinit var updateViewModel: TreatmentUpdateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.treatment_update_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateViewModel = ViewModelProvider(this).get(TreatmentUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}