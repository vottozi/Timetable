package com.example.timetable.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.network.Resource
import com.example.timetable.data.repository.AuthRepository
import com.example.timetable.data.responses.LoginResponse
import com.example.timetable.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    fun login(
        email: String,
        passwd: String
    ) {
        viewModelScope.launch {
            try {
                _loginResponse.value = Resource.Loading
                val response: LoginResponse = repository.login(email, passwd)
                _loginResponse.value = Resource.Success(response)
                // Handle the response
                saveAuthToken(response.token)
                saveUserRole(response.role)
            } catch (e: Exception) {
                _loginResponse.value = Resource.Error(e.message ?: "Unknown error")
            }
        }
    }

    suspend fun saveAuthToken(token: String) {
        repository.saveAuthToken(token)
    }

    private suspend fun saveUserRole(role: String) {
        repository.saveUserRole(role)
    }
}