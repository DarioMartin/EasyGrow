package com.dariomartin.easygrow.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dariomartin.easygrow.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        supportActionBar?.hide()

        setContentView(binding.root)
    }

}
