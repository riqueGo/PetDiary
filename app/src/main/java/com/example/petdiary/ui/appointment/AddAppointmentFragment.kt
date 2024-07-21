package com.example.petdiary.ui.appointment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentAddAppointmentBinding

class AddAppointmentFragment : Fragment() {

    private var _binding: FragmentAddAppointmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancelAppointment.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnSaveAppointment.setOnClickListener {
            // Logic to save appointment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
