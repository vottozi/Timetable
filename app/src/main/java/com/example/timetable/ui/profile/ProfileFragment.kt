package com.example.timetable.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.timetable.data.network.Resource
import com.example.timetable.data.UserPreferences
import com.example.timetable.data.network.UserApi
import com.example.timetable.data.repository.UserRepository
import com.example.timetable.data.responses.LoginResponse
import com.example.timetable.databinding.FragmentProfileBinding
import com.example.timetable.ui.base.BaseFragment
import com.example.timetable.ui.base.ViewModelFactory
import com.example.timetable.ui.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class ProfileFragment : BaseFragment<ProfileViewModel, FragmentProfileBinding, UserRepository>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userPreferences = UserPreferences.getInstance(requireContext())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Инициализация ViewModel после инициализации binding
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())

        binding.progressbar.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.data)
                }

                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }

                is Resource.Error -> {
                    binding.progressbar.visible(false)
                    // Handle error here
                }

                else -> {
                    binding.progressbar.visible(false)
                    // Handle other cases if any
                }
            }
        })

        binding.buttonExit.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(loginResponse: LoginResponse) {
        with(binding) {
            firstName.text = loginResponse.name
            secondName.text = loginResponse.surname
            roletv.text = loginResponse.role
            //groupCourse.text = loginResponse.group
            email.text = loginResponse.email
            if (loginResponse.group.isNullOrEmpty()) {
                groupll.visibility = View.GONE
            } else {
                groupll.visibility = View.VISIBLE
                groupCourse.text = loginResponse.group
            }
        }
    }

    override fun getViewModel() = ProfileViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = remoteDataSource.buildApi(UserApi::class.java, token)
        return UserRepository(api)
    }
}
