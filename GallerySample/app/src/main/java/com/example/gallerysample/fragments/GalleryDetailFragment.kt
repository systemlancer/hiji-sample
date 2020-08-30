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
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.gallerysample.adapters.PhotoAdapter
import com.example.gallerysample.adapters.PhotoDetailAdapter
import com.example.gallerysample.databinding.FragmentGalleryDetailBinding
import com.example.gallerysample.dialogs.PhotoDeleteDialog
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
        var photoDetailAdapter: PhotoDetailAdapter? = null
        binding = FragmentGalleryDetailBinding.inflate(inflater, container, false)
            .apply {
                lifecycleOwner = viewLifecycleOwner
                photoDetailAdapter = PhotoDetailAdapter(PhotoAdapter.DeleteOnClickListener {
                    activity?.let { galleryActivity ->
                        PhotoDeleteDialog(it).show(
                            galleryActivity.supportFragmentManager,
                            null
                        )
                    }
                })
                val pagerSnapHelper = PagerSnapHelper()
                pagerSnapHelper.attachToRecyclerView(viewPager)
                viewPager.adapter = photoDetailAdapter
                viewPager.layoutManager?.scrollToPosition(args.selectedPosition)
            }

        viewModel.uriList.observe(viewLifecycleOwner) { photoUriList ->
            photoDetailAdapter?.submitList(photoUriList)
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            viewModel.clear()
            photoDetailAdapter?.currentPosition?.let {
                findNavController().navigate(
                    GalleryDetailFragmentDirections.actionGalleryDetailFragmentToGalleryFragment(it)
                )
            }

        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clear()
    }
}