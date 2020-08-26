package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.gallerysample.adapters.PhotoListAdapter
import com.example.gallerysample.databinding.FragmentGalleryBinding
import com.example.gallerysample.utilities.InjectorUtils
import com.example.gallerysample.viewmodels.GalleryViewModel

class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by viewModels {
        InjectorUtils.provideGalleryViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                val photoList = viewModel.getPhotoUriList()
                val photoListAdapter =
                    PhotoListAdapter(this@GalleryFragment, PhotoListAdapter.OnClickListener {
                    })

                photosGrid.adapter = photoListAdapter
                photoListAdapter.submitList(photoList)
            }
        return binding.root
    }
}