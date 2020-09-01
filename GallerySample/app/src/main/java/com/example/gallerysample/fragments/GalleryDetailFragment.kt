package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.gallerysample.adapters.PhotoDetailAdapter
import com.example.gallerysample.databinding.FragmentGalleryDetailBinding
import com.example.gallerysample.dialogs.PhotoDeleteDialog
import com.example.gallerysample.viewmodels.GalleryViewModel

class GalleryDetailFragment : Fragment() {

    private val viewModel: GalleryViewModel by activityViewModels()
    private val args: GalleryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val photoDetailAdapter: PhotoDetailAdapter
        val binding = FragmentGalleryDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                photoDetailAdapter = PhotoDetailAdapter(
                    args.selectedPosition,
                    PhotoDetailAdapter.DeleteOnClickListener {
                        activity?.let { galleryActivity ->
                            PhotoDeleteDialog(it).show(
                                galleryActivity.supportFragmentManager,
                                null
                            )
                        }
                    })

                photoDetailAdapter.submitList(viewModel.uriList.value)
                viewPager.adapter = photoDetailAdapter
                viewPager.setCurrentItem(args.selectedPosition, false)
            }

        viewModel.apply {
            uriList.observe(viewLifecycleOwner) {
                photoDetailAdapter.submitList(it)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(
                GalleryDetailFragmentDirections.actionGalleryDetailFragmentToGalleryFragment(
                    binding.viewPager.currentItem
                )
            )
        }

        return binding.root
    }
}