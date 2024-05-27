package com.example.timetable.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timetable.data.network.Resource
import com.example.timetable.data.repository.UserRepository
import com.example.timetable.data.responses.LoginResponse
import com.example.timetable.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ProfileViewModel (
    private val repository: UserRepository
): BaseViewModel(repository) {

    private val _user: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val user: LiveData<Resource<LoginResponse>>
        get() = _user

    fun getUser() = viewModelScope.launch {
        _user.value = Resource.Loading
        _user.value = repository.getUser()
    }

}