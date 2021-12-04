package com.dariomartin.easygrow.presentation.sanitary.druglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.databinding.FragmentSanitaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrugsTabFragment : Fragment() {

    companion object {
        fun newInstance(): Fragment {
            return DrugsTabFragment()
        }
    }

    private lateinit var pageViewModel: DrugsTabViewModel
    private var _binding: FragmentSanitaryBinding? = null
    private val binding get() = _binding!!

    private val adapter = DrugsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this)[DrugsTabViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSanitaryBinding.inflate(inflater, container, false)
        val root = binding.root

        setupRecyclerView()

        pageViewModel.drugs.observe(viewLifecycleOwner, { drugs ->
            if (drugs.isNullOrEmpty()) {
                showEmptyMessage()
            } else {
                listPatients(drugs)
            }
        })
        return root
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun listPatients(drugs: List<Drug>) {
        adapter.setDrugs(drugs)
    }

    private fun showEmptyMessage() {


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}