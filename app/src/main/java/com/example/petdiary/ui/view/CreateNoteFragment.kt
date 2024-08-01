package com.example.petdiary.ui.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentCreateNoteBinding
import com.example.petdiary.domain.model.DiaryNote
import com.example.petdiary.domain.model.Pet
import com.example.petdiary.ui.components.CalendarPicker
import com.example.petdiary.ui.viewmodel.CreateNoteViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private val createNoteViewModel: CreateNoteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (createNoteViewModel.pets.isInitialized) {
            binding.petsMultiSelect.setItems(createNoteViewModel.getPetsNames()!!)
        }

        binding.dateText.setOnClickListener {
            CalendarPicker.showDatePicker(parentFragmentManager) { setDate(it) }
        }

        binding.saveButton.setOnClickListener {
            addDiaryNote()
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun addDiaryNote() {
        val title = binding.titleEditText.text.toString()
        val content = binding.contentEditText.text.toString()
        val selectedPetNames = binding.petsMultiSelect.checkedItems
        val selectedPets = createNoteViewModel.pets.value?.filter { selectedPetNames.contains(it.name) }
        val note = DiaryNote(
            id = System.currentTimeMillis(),
            title = title,
            content = content,
            date = createNoteViewModel.date,
            pets = selectedPets ?: emptyList(),
            images = emptyList()
        )
        createNoteViewModel.saveNote(note)
        findNavController().navigateUp()
    }

    private fun setDate(date: LocalDate) {
        createNoteViewModel.setDate(date)
        binding.dateText.setText(date.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}