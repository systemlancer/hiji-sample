package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.gallerysample.adapters.PhotoAdapter
import com.example.gallerysample.databinding.FragmentGalleryDetailBinding
import com.example.gallerysample.utilities.InjectorUtils
import com.example.gallerysample.viewmodels.GalleryViewModel

class GalleryDetailFragment : Fragment() {

    private lateinit var binding: FragmentGalleryDetailBinding
    private val galleryViewModel: GalleryViewModel by activityViewModels {
        InjectorUtils.provideGalleryViewModelFactory(requireContext())
    }
    private val args: GalleryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGalleryDetailBinding.inflate(inflater, container, false)
            .apply {
                val photoAdapter = PhotoAdapter(galleryViewModel.uriList)
                viewPager.adapter = photoAdapter
                viewPager.currentItem = args.selectedPosition
            }
        return binding.root
    }
}