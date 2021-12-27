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
import com.dariomartin.easygrow.R
import com.dariomartin.easygrow.databinding.FragmentSignUpBinding
import com.dariomartin.easygrow.utils.Extensions.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var loginViewModel: AuthViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private var form: SignUpForm = SignUpForm()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
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

        loginViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        signUp.isEnabled = false

        loginViewModel.signUpResult.observe(viewLifecycleOwner, Observer {
            val signUpResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (signUpResult.error != null) {
                showLoginFailed(signUpResult.error)
            } else if (signUpResult.success) {
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
                    EditorInfo.IME_ACTION_DONE -> signUp()
                }
                false
            }
        }

        signUp.setOnClickListener {
            signUp()
        }

        login.setOnClickListener {
            val action = SignUpFragmentDirections.actionNavigationSignUpToNavigationLogin()
            findNavController().navigate(action)
        }

    }

    private fun signUp() {
        if (form.isValid()) {
            binding.loading.visibility = View.VISIBLE
            loginViewModel.signUp(
                form.name!!,
                form.surname!!,
                form.email!!,
                form.password!!
            )
        } else {
            showErrors()
        }
    }

    private fun onDataChanged(
        name: AppCompatEditText,
        surname: AppCompatEditText,
        email: AppCompatEditText,
        password: AppCompatEditText
    ) {
        hideErrors()
        form = SignUpForm(
            name.text.toString(),
            surname.text.toString(),
            email.text.toString(),
            password.text.toString()
        )
        binding.signUp.isEnabled = form.isValid()
    }

    private fun showErrors() {
        if (!form.isValidName()) binding.nameInput.error =
            requireContext().getString(R.string.invalid_name)
        if (!form.isValidSurname()) binding.surnameInput.error =
            requireContext().getString(R.string.invalid_surname)
        if (!form.isValidEmail()) binding.emailInput.error =
            requireContext().getString(R.string.invalid_email)
        if (!form.isValidPassword()) binding.passwordInput.error =
            requireContext().getString(R.string.invalid_password)
    }

    private fun hideErrors() {
        binding.nameInput.error = null
        binding.surnameInput.error = null
        binding.emailInput.error = null
        binding.passwordInput.error = null
    }

    private fun onSighUpSuccess() {
        val action = SignUpFragmentDirections.actionNavigationSignUpToNavigationTypeSelection()
        findNavController().navigate(action)
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(requireContext(), errorString, Toast.LENGTH_SHORT).show()
    }
}