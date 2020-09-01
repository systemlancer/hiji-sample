package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gallerysample.adapters.PhotoListAdapter
import com.example.gallerysample.databinding.FragmentGalleryBinding
import com.example.gallerysample.utilities.InjectorUtils
import com.example.gallerysample.viewmodels.GalleryViewModel

class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by activityViewModels {
        InjectorUtils.provideGalleryViewModelFactory(requireContext())
    }
    private val args: GalleryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val photoListAdapter: PhotoListAdapter
        val binding = FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner

                photoListAdapter =
                    PhotoListAdapter(PhotoListAdapter.OnClickListener { position ->
                        findNavController().navigate(
                            GalleryFragmentDirections.actionGalleryFragmentToGalleryDetailFragment(
                                position
                            )
                        )
                    })

                photosGrid.layoutManager?.scrollToPosition(args.position)
                photosGrid.adapter = photoListAdapter
            }

        viewModel.photoUriList.observe(viewLifecycleOwner) {
            photoListAdapter.submitList(it)
        }

        return binding.root
    }
}