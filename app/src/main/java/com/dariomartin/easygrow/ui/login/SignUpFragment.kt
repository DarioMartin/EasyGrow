package com.dariomartin.easygrow.ui.login

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
import com.dariomartin.easygrow.databinding.FragmentSignUpBinding
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = binding.name
        val surname = binding.surname
        val email = binding.email
        val password = binding.password
        val signUp = binding.signUp
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        loginViewModel.signUpFormState.observe(viewLifecycleOwner, Observer {
            val signUpState = it ?: return@Observer

            signUp.isEnabled = signUpState.isDataValid

            if (signUpState.usernameError != null) {
                email.error = getString(signUpState.usernameError)
            }
            if (signUpState.passwordError != null) {
                password.error = getString(signUpState.passwordError)
            }
        })

        loginViewModel.signUpResult.observe(viewLifecycleOwner, Observer {
            val signUpResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (signUpResult.error != null) {
                showLoginFailed(signUpResult.error)
            }
            if (signUpResult.success != null) {
                onSighUpSuccess()
            }
            requireActivity().setResult(Activity.RESULT_OK)
        })

        name.afterTextChanged {
            onDataChanged(name, surname, email, password)
        }

        surname.afterTextChanged {
            onDataChanged(name, surname, email, password)
        }

        email.afterTextChanged {
            onDataChanged(name, surname, email, password)
        }

        password.apply {
            afterTextChanged {
                onDataChanged(name, surname, email, password)
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        signUp(name, surname, email, password)
                }
                false
            }

            signUp.setOnClickListener {
                loading.visibility = View.VISIBLE
                signUp(name, surname, email, password)
            }
        }

        login.setOnClickListener {
            val action = SignUpFragmentDirections.actionNavigationSignUpToNavigationLogin()
            findNavController().navigate(action)
        }

    }

    private fun signUp(
        name: AppCompatEditText,
        surname: AppCompatEditText,
        email: AppCompatEditText,
        password: AppCompatEditText
    ) {
        loginViewModel.signUp(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun onDataChanged(
        name: AppCompatEditText,
        surname: AppCompatEditText,
        email: AppCompatEditText,
        password: AppCompatEditText
    ) {
        loginViewModel.signUpDataChanged(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
    }

    private fun onSighUpSuccess() {
        val action = SignUpFragmentDirections.actionNavigationSignUpToNavigationTypeSelection()
        findNavController().navigate(action)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }
}