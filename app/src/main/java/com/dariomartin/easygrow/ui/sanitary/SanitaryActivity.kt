package com.dariomartin.easygrow.ui.sanitary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.dariomartin.easygrow.databinding.ActivitySanitaryBinding
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SanitaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySanitaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySanitaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = TabsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
    }
}