package com.dariomartin.easygrow.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.data.model.User
import com.dariomartin.easygrow.databinding.ActivityMainBinding
import com.dariomartin.easygrow.ui.login.LoginActivity
import com.dariomartin.easygrow.ui.patient.PatientActivity
import com.dariomartin.easygrow.ui.sanitary.SanitaryActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        supportActionBar?.hide()

        binding.patientButton.setOnClickListener { viewModel.setUserType(User.Type.PATIENT) }
        binding.sanitaryButton.setOnClickListener { viewModel.setUserType(User.Type.SANITARY) }
        binding.logout.setOnClickListener { logout() }

        viewModel.userType.observe(this@MainActivity, { userType ->
            userType?.let {
                when (it) {
                    User.Type.PATIENT -> goToPatient()
                    User.Type.SANITARY -> goToSanitary()
                }
            }
        })
    }

    private fun goToPatient() {
        val switchActivityIntent = Intent(this, PatientActivity::class.java)
        startActivity(switchActivityIntent)
        finish()
    }

    private fun goToSanitary() {
        val switchActivityIntent = Intent(this, SanitaryActivity::class.java)
        startActivity(switchActivityIntent)
        finish()
    }

    private fun logout() {
        viewModel.logout()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}