package com.example.petdiary.ui.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentDiaryNoteBinding
import com.example.petdiary.ui.adapters.ImageCarouselAdapter
import com.example.petdiary.ui.model.DiaryNote

class DiaryNoteFragment : Fragment() {

    private var _binding: FragmentDiaryNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var diaryNote: DiaryNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiaryNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            diaryNote = it.getParcelable("diaryNote")!!
            displayNoteDetails()
        }

        binding.returnButton.setOnClickListener { findNavController().navigateUp() }

        binding.deleteButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun displayNoteDetails() {
        binding.titleTextView.text = diaryNote.title
        binding.contentTextView.text = diaryNote.content
        binding.dateTextView.text = diaryNote.date
        val imageUris = diaryNote.images.map { Uri.parse(it) }
        val imageAdapter = ImageCarouselAdapter(requireContext(), imageUris)
        binding.imageCarousel.adapter = imageAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
