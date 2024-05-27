package com.example.timetable.ui.adminpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timetable.R
import com.example.timetable.databinding.FragmentDaySelectionBinding

class DaySelectionFragment : Fragment() {

    private val args: DaySelectionFragmentArgs by navArgs()
    private var _binding: FragmentDaySelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDaySelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekNumber = args.weekNumber

        val days = listOf(
            binding.mondayButton, binding.tuesdayButton, binding.wednesdayButton,
            binding.thursdayButton, binding.fridayButton, binding.saturdayButton
        )

        val dayNames = listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

        days.forEachIndexed { index, button ->
            button.setOnClickListener {
                val action = DaySelectionFragmentDirections
                    .actionDaySelectionFragmentToTimeSlotSelectionFragment(weekNumber, dayNames[index])
                findNavController().navigate(action)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {}
}