package com.example.timetable.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.timetable.R
import com.example.timetable.data.UserPreferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartFragment : Fragment() {
    private lateinit var userPreferences: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userPreferences = UserPreferences.getInstance(requireContext())

        lifecycleScope.launch {
            val role = userPreferences.role.first()
            if (role == "admin") {
                findNavController().navigate(R.id.adminFragment)
            } else {
                findNavController().navigate(R.id.scheduleFragment)
            }
        }
    }
}