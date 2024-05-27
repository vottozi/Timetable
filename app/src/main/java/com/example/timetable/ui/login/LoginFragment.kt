package com.example.timetable.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.timetable.MainActivity
import com.example.timetable.data.network.Resource
import com.example.timetable.data.network.AuthApi
import com.example.timetable.data.repository.AuthRepository
import com.example.timetable.databinding.FragmentLoginBinding
import com.example.timetable.ui.base.BaseFragment
import com.example.timetable.ui.enable
import com.example.timetable.ui.handleApiError
import com.example.timetable.ui.startNewActivity
import com.example.timetable.ui.visible
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.buttonLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {

                    lifecycleScope.launch {
                        //Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_LONG).show()
                        viewModel.saveAuthToken(it.data.token)
                        requireActivity().startNewActivity(MainActivity::class.java)
                    }
                }

                is Resource.Failure -> handleApiError(it){ login() }

                else -> {}
            }
        })

        binding.editTextTextPassword.addTextChangedListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            binding.buttonLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }


        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val passwd = binding.editTextTextPassword.text.toString().trim()
        viewModel.login(email, passwd)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(remoteDataSource.buildApi(AuthApi::class.java), userPreferences)

}