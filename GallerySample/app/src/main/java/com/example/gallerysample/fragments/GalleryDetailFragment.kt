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
                    PhotoDetailAdapter.DeleteOnClickListener { uri ->
                        activity?.let { galleryActivity ->
                            PhotoDeleteDialog(uri).show(
                                galleryActivity.supportFragmentManager,
                                PHOTO_DELETE_DIALOG
                            )
                        }
                    })

                viewPager.adapter = photoDetailAdapter
            }

        viewModel.photoUriList.observe(viewLifecycleOwner) {
            photoDetailAdapter.submitList(it) {
                with(binding.viewPager) {
                    if (currentItem == 0) {
                        setCurrentItem(args.selectedPosition, false)
                    }
                }
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

    companion object {
        private const val PHOTO_DELETE_DIALOG = "PhotoDeleteDialog"
    }
}