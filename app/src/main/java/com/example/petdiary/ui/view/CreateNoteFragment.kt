package com.example.petdiary.ui.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.petdiary.databinding.FragmentCreateNoteBinding
import com.example.petdiary.domain.model.DiaryNote
import com.example.petdiary.ui.adapters.ImageCarouselAdapter
import com.example.petdiary.ui.components.CalendarPicker
import com.example.petdiary.ui.viewmodel.CreateNoteViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class CreateNoteFragment : Fragment() {

    private var _binding: FragmentCreateNoteBinding? = null
    private val binding get() = _binding!!

    private val createNoteViewModel: CreateNoteViewModel by viewModels()
    private val pickImagesRequestCode = 1001

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

        binding.addImageButton.setOnClickListener {
            pickImages()
        }

        createNoteViewModel.selectedImages.observe(viewLifecycleOwner) { images ->
            updateImageCarousel(images)
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
            date = createNoteViewModel.date!!,
            pets = selectedPets ?: emptyList(),
            images = createNoteViewModel.selectedImages.value ?: emptyList()
        )
        createNoteViewModel.saveNote(note)
        findNavController().navigateUp()
    }

    private fun setDate(date: LocalDate) {
        createNoteViewModel.setDate(date)
        binding.dateText.setText(date.toString())
    }

    private fun pickImages() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(intent, pickImagesRequestCode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pickImagesRequestCode && resultCode == Activity.RESULT_OK) {
            data?.let { intent ->
                intent.clipData?.let { clipData ->
                    for (i in 0 until clipData.itemCount) {
                        val uri = clipData.getItemAt(i).uri
                        createNoteViewModel.addImage(uri)
                    }
                } ?: intent.data?.let { uri ->
                    createNoteViewModel.addImage(uri)
                }
            }
        }
    }

    private fun updateImageCarousel(images: List<Uri>) {
        val adapter = ImageCarouselAdapter(requireContext(), images)
        binding.imageCarousel.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
