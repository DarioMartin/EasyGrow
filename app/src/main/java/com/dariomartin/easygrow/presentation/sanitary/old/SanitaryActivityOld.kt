package com.dariomartin.easygrow.presentation.sanitary.old

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.ActivitySanitaryOldBinding
import com.dariomartin.easygrow.presentation.login.AuthActivity
import com.dariomartin.easygrow.presentation.sanitary.SanitaryViewModel
import com.dariomartin.easygrow.presentation.sanitary.TabsPagerAdapter
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SanitaryActivityOld : AppCompatActivity() {

    private lateinit var viewModel: SanitaryViewModel
    private lateinit var binding: ActivitySanitaryOldBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySanitaryOldBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SanitaryViewModel::class.java]

        setUpTabs()
    }

    private fun setUpTabs() {
        val sectionsPagerAdapter = TabsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.sanitary_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        viewModel.logout()
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}