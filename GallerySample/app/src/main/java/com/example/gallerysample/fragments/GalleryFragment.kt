package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.gallerysample.adapters.PhotoListAdapter
import com.example.gallerysample.databinding.FragmentGalleryBinding
import com.example.gallerysample.utilities.InjectorUtils
import com.example.gallerysample.viewmodels.GalleryViewModel

class GalleryFragment : Fragment() {

    private val viewModel: GalleryViewModel by activityViewModels {
        InjectorUtils.provideGalleryViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var photoListAdapter: PhotoListAdapter
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

                photosGrid.adapter = photoListAdapter
            }

        viewModel.apply {

            uriList.observe(viewLifecycleOwner) {
                photoListAdapter.submitList(it)
            }
        }

        return binding.root
    }

//    override fun onResume() {
//        super.onResume()
//        binding.photosGrid.adapter?.registerAdapterDataObserver(viewModel.adapterDataObserver)
//    }
//
//    override fun onPause() {
//        super.onPause()
//        binding.photosGrid.adapter?.unregisterAdapterDataObserver(viewModel.adapterDataObserver)
//    }
}