package com.dariomartin.easygrow.presentation.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<Binding : ViewBinding, VM : ViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    private var _binding: Binding? = null
    protected val binding: Binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = provideViewModel()
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): Binding
    abstract fun provideViewModel(): VM

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}