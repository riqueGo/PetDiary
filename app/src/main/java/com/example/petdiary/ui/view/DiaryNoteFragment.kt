package com.example.petdiary.ui.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentDiaryNoteBinding
import com.example.petdiary.repository.DiaryNoteRepository
import com.example.petdiary.ui.adapters.ImageCarouselAdapter
import com.example.petdiary.ui.model.DiaryNote
import kotlinx.coroutines.launch

class DiaryNoteFragment : Fragment() {

    private var _binding: FragmentDiaryNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var diaryNote: DiaryNote
    private lateinit var diaryNoteRepository: DiaryNoteRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        diaryNoteRepository = DiaryNoteRepository(requireContext().applicationContext)

        arguments?.let {
            diaryNote = it.getParcelable("diaryNote")!!
            displayNoteDetails()
        }

        binding.returnButton.setOnClickListener { findNavController().navigateUp() }

        binding.deleteButton.setOnClickListener { deleteNote() }
    }

    private fun displayNoteDetails() {
        binding.titleTextView.text = diaryNote.title
        binding.contentTextView.text = diaryNote.content
        binding.dateTextView.text = diaryNote.date
        val imageUris = diaryNote.images.map { Uri.parse(it) }
        val imageAdapter = ImageCarouselAdapter(requireContext(), imageUris)
        binding.imageCarousel.adapter = imageAdapter
    }

    private fun deleteNote() {
        lifecycleScope.launch {
            diaryNoteRepository.deleteNote(diaryNote.id)
            Toast.makeText(requireContext(), "Note deleted successfully.", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
