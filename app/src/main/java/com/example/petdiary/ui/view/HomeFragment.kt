package com.example.petdiary.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.petdiary.R
import com.example.petdiary.databinding.FragmentHomeBinding
import com.example.petdiary.ui.adapters.DiaryAdapter
import com.example.petdiary.ui.model.DiaryNote
import com.example.petdiary.ui.viewmodel.HomeViewModel
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var diaryAdapter: DiaryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val expandableListView = binding.expandableListView

        homeViewModel.yearSections.observe(viewLifecycleOwner) { sections ->
            diaryAdapter = DiaryAdapter(requireContext(), sections)
            expandableListView.setAdapter(diaryAdapter)

            if (diaryAdapter.groupCount > 0) {
                expandableListView.expandGroup(0)
            }
        }

        expandableListView.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val selectedNote = diaryAdapter.getChild(groupPosition, childPosition) as DiaryNote

            val bundle = Bundle().apply {
                putParcelable("diaryNote", selectedNote)
            }

            findNavController().navigate(R.id.action_homeFragment_to_displayNoteFragment, bundle)
            true
        }

        binding.fabAddDailyNote.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_createNoteFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
