package com.example.timetable.ui.adminpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timetable.R
import com.example.timetable.databinding.FragmentTimeslotSelectionBinding

class TimeSlotSelectionFragment : Fragment() {

    private val args: TimeSlotSelectionFragmentArgs by navArgs()
    private var _binding: FragmentTimeslotSelectionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimeslotSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekNumber = args.weekNumber
        val dayOfWeek = args.dayOfWeek

        val timeSlots = listOf(
            binding.firstSlotButton, binding.secondSlotButton, binding.thirdSlotButton,
            binding.fourthSlotButton, binding.fifthSlotButton, binding.sixthSlotButton,
            binding.seventhSlotButton, binding.eighthSlotButton, binding.ninthSlotButton
        )

        timeSlots.forEachIndexed { index, button ->
            button.setOnClickListener {
                val timeSlot = index + 1
                val action = TimeSlotSelectionFragmentDirections
                    .actionTimeSlotSelectionFragmentToEditTimeSlotFragment(weekNumber, dayOfWeek, timeSlot)
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
}