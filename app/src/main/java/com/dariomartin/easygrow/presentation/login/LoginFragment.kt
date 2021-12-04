package com.dariomartin.easygrow.presentation.login

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dariomartin.easygrow.databinding.FragmentLoginBinding
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var loginViewModel: AuthViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = binding.email
        val password = binding.password
        val login = binding.login
        val signUp = binding.signUp
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        loginViewModel.signUpFormState.observe(viewLifecycleOwner, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.emailInput.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.passwordInput.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.authResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE

            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            } else if (loginResult.success != null) {
                onLoginSuccess()
            }
            requireActivity().setResult(Activity.RESULT_OK)
        })

        email.afterTextChanged {
            loginViewModel.loginDataChanged(
                email.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    email.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE -> login(email, password)
                }
                false
            }

            login.setOnClickListener {
                login(email, password)
            }


        }

        signUp.setOnClickListener {
            val action = LoginFragmentDirections.actionNavigationLoginToNavigationSignUp()
            findNavController().navigate(action)
        }
    }

    private fun login(
        email: AppCompatEditText,
        password: AppCompatEditText
    ) {
        binding.loading.visibility = View.VISIBLE
        loginViewModel.login(email.text.toString(), password.text.toString())
    }

    private fun onLoginSuccess() {
        val action = LoginFragmentDirections.actionNavigationLoginToNavigationTypeSelection()
        findNavController().navigate(action)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }

}