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
    private val viewModel: GalleryViewModel by activityViewModels {
        InjectorUtils.provideGalleryViewModelFactory(
            requireActivity().application,
            requireContext()
        )
    }
    private val args: GalleryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var photoAdapter: PhotoAdapter? = null
        binding = FragmentGalleryDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
            }

        viewModel.uriList.observe(viewLifecycleOwner) { photoUriList ->
            photoAdapter.let {
                if (it == null) {
                    photoAdapter = PhotoAdapter(photoUriList)
                    binding.viewPager.apply {
                        adapter = photoAdapter
                        currentItem = args.selectedPosition
                    }
                } else {
                    it.photoUriList = photoUriList
                    it.notifyDataSetChanged()
                }
            }

        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
}