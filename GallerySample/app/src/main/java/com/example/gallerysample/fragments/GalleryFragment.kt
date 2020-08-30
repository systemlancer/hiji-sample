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

    private lateinit var binding: FragmentGalleryBinding
    private val viewModel: GalleryViewModel by activityViewModels {
        InjectorUtils.provideGalleryViewModelFactory(
            requireActivity().application,
            requireContext()
        )
    }
    private val args: GalleryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var photoListAdapter: PhotoListAdapter
        binding = FragmentGalleryBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                photoListAdapter =
                    PhotoListAdapter(PhotoListAdapter.OnClickListener {
                        viewModel.clear()
                        findNavController().navigate(
                            GalleryFragmentDirections.actionGalleryFragmentToGalleryDetailFragment(
                                it
                            )
                        )
                    })

                photosGrid.adapter = photoListAdapter
                viewModel.loadPhotoUris()
                photosGrid.layoutManager?.scrollToPosition(args.position)
            }

        viewModel.uriList.observe(viewLifecycleOwner) {
            photoListAdapter.submitList(it)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }
}