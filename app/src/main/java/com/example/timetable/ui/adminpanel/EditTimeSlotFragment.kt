package com.example.timetable.ui.adminpanel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.timetable.R
import com.example.timetable.data.network.RetrofitInstance
import com.example.timetable.databinding.FragmentEditTimeslotBinding
import com.mcdev.filledboxspinner.FilledBoxSpinner
import com.mcdev.filledboxspinner.OnItemSelectedListener
import kotlinx.coroutines.launch

class EditTimeSlotFragment : Fragment() {

    private val args: EditTimeSlotFragmentArgs by navArgs()
    private var _binding: FragmentEditTimeslotBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTimeslotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weekNumber = args.weekNumber
        val dayOfWeek = args.dayOfWeek
        val timeSlot = args.timeSlot

        setupSpinners()

        binding.buttonSave.setOnClickListener {

        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupSpinners() {
        setupSpinner(binding.groupSpinner) { RetrofitInstance.api.getGroups() }
        setupSpinner(binding.subjectSpinner) { RetrofitInstance.api.getSubjects() }
        setupSpinner(binding.roomSpinner) { RetrofitInstance.api.getRooms() }
        setupSpinner(binding.teacherSpinner) { RetrofitInstance.api.getTeachers() }
    }

    private fun <T> setupSpinner(spinner: FilledBoxSpinner, dataFetcher: suspend () -> List<T>) {
        lifecycleScope.launch {
            try {
                val data = dataFetcher().map { it.toString() } // Convert data to a list of strings
                spinner.setItems(data)
                spinner.isSearchable(false) // Assuming you don't want the spinner to be searchable

                spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
                    override fun onItemSelected(itemValue: String) {
                        handleItemSelected(spinner, itemValue)
                    }
                })

            } catch (e: Exception) {
                // Handle exception
                Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleItemSelected(spinner: FilledBoxSpinner, itemValue: String) {
        when (spinner.id) {
            R.id.groupSpinner -> {
                // Handle group spinner item selection //здесь нужно будет писать логику для обработки каждого элемента этих списков
                Toast.makeText(requireContext(), "Selected group: $itemValue", Toast.LENGTH_SHORT).show()
            }
            R.id.subjectSpinner -> {
                // Handle subject spinner item selection
                Toast.makeText(requireContext(), "Selected subject: $itemValue", Toast.LENGTH_SHORT).show()
            }
            R.id.roomSpinner -> {
                // Handle room spinner item selection
                Toast.makeText(requireContext(), "Selected room: $itemValue", Toast.LENGTH_SHORT).show()
            }
            R.id.teacherSpinner -> {
                // Handle teacher spinner item selection
                Toast.makeText(requireContext(), "Selected teacher: $itemValue", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}