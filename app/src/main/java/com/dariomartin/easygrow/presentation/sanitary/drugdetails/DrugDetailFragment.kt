package com.dariomartin.easygrow.presentation.sanitary.drugdetails

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.data.model.Drug
import com.dariomartin.easygrow.databinding.DrugDetailFragmentBinding
import com.dariomartin.easygrow.presentation.utils.AppWebViewClient
import com.dariomartin.easygrow.presentation.utils.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DrugDetailFragment : BaseFragment<DrugDetailFragmentBinding, DrugDetailViewModel>() {

    private val args: DrugDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel.getDrug(args.drugId).observe(viewLifecycleOwner, { drug ->
            drug?.let { paintDrug(it) }
        })
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): DrugDetailFragmentBinding {
        return DrugDetailFragmentBinding.inflate(inflater, container, false)
    }

    override fun provideViewModel(): DrugDetailViewModel {
        return ViewModelProvider(this)[DrugDetailViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.drug_details_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                goToEdition()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToEdition() {
        val action =
            DrugDetailFragmentDirections.actionDrugDetailFragmentToCreateDrugFragment(args.drugId)
        findNavController().navigate(action)
    }

    private fun paintDrug(drug: Drug) {
        binding.header.name.text = drug.name
        binding.header.pharmacy.text = drug.pharmacy
        binding.header.cartridgeVolume.text = drug.getCartridgeVolumeString()
        binding.header.concentration.text = drug.getConcentrationString()

        if (drug.url.isEmpty()) {
            showNoUrlMessage()
        } else {
            loadWeb(drug.url)
        }
    }

    private fun loadWeb(url: String) {
        binding.errorMessage.errorMessage.visibility = View.GONE
        binding.webView.webViewClient = AppWebViewClient(binding.progressBar)
        binding.webView.loadUrl(url)
    }

    private fun showNoUrlMessage() {
        binding.errorMessage.errorMessage.visibility = View.VISIBLE
    }

}