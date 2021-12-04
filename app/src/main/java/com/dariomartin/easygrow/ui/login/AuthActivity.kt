package com.dariomartin.easygrow.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dariomartin.easygrow.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)
    }

}
