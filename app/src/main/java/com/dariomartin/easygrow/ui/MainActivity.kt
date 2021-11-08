package com.dariomartin.easygrow.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.patientButton.setOnClickListener { goToPatient() }
        binding.sanitaryButton.setOnClickListener { goToSanitary() }
        binding.logout.setOnClickListener { logout() }

    }

    private fun goToPatient() {
        val switchActivityIntent = Intent(this, PatientActivity::class.java)
        startActivity(switchActivityIntent)
    }

    private fun goToSanitary() {

    }

    private fun logout() {

    }
}