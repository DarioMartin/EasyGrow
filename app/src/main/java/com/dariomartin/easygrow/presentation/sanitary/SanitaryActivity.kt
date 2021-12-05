package com.dariomartin.easygrow.presentation.sanitary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.ActivitySanitaryBinding
import com.dariomartin.easygrow.presentation.login.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SanitaryActivity : AppCompatActivity() {

    private lateinit var viewModel: SanitaryViewModel
    private lateinit var binding: ActivitySanitaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySanitaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[SanitaryViewModel::class.java]
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