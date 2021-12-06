package com.dariomartin.easygrow.presentation.sanitary.druglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.databinding.FragmentSanitaryBinding
import com.dariomartin.easygrow.presentation.sanitary.tabs.TabItemListener
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import com.dariomartin.easygrow.presentation.utils.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrugsTabFragment : BaseFragment<FragmentSanitaryBinding, DrugsTabViewModel>() {

    companion object {
        fun newInstance(listener: TabItemListener): Fragment {
            val fragment = DrugsTabFragment()
            fragment.listener = listener
            return fragment
        }
    }

    private var listener: TabItemListener? = null
    private val adapter = DrugsAdapter { drug -> listener?.onTabItemClicked(drug) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getDrugs().observe(viewLifecycleOwner, { drugs ->
            if (drugs.isNullOrEmpty()) {
                showEmptyMessage()
            } else {
                listDrugs(drugs)
            }
        })
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

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.recyclerView.adapter as DrugsAdapter
                val drug: Drug = adapter.getItem(viewHolder.adapterPosition)
                viewModel.removeDrug(drug.name)
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun listDrugs(drugs: List<Drug>) {
        adapter.setDrugs(drugs)
    }

    private fun showEmptyMessage() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSanitaryBinding {
        return FragmentSanitaryBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): DrugsTabViewModel {
        return ViewModelProvider(this)[DrugsTabViewModel::class.java]
    }
}