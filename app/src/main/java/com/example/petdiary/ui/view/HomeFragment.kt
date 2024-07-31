package com.example.petdiary.ui.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petdiary.R
import com.example.petdiary.databinding.FragmentHomeBinding
import com.example.petdiary.databinding.FragmentScheduleBinding
import com.example.petdiary.ui.adapters.DiaryAdapter
import com.example.petdiary.ui.viewmodel.HomeViewModel
@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private lateinit var diaryAdapter: DiaryAdapter
    private lateinit var expandableListView: ExpandableListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expandableListView = binding.expandableListView

        homeViewModel.yearSections.observe(viewLifecycleOwner, Observer { sections ->
            diaryAdapter = DiaryAdapter(requireContext(), sections)
            expandableListView.setAdapter(diaryAdapter)

            for (i in 0 until diaryAdapter.groupCount) {
                expandableListView.expandGroup(i)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}