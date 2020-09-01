package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.gallerysample.adapters.PhotoAdapter
import com.example.gallerysample.adapters.PhotoDetailAdapter
import com.example.gallerysample.databinding.FragmentGalleryDetailBinding
import com.example.gallerysample.dialogs.PhotoDeleteDialog
import com.example.gallerysample.viewmodels.GalleryViewModel
import timber.log.Timber

class GalleryDetailFragment : Fragment() {

    private lateinit var binding: FragmentGalleryDetailBinding
    private val viewModel: GalleryViewModel by activityViewModels()
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

                photoDetailAdapter?.submitList(viewModel.uriList.value)
                viewPager.adapter = photoDetailAdapter
                viewPager.currentItem = args.selectedPosition
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

    override fun onStart() {
        super.onStart()
        binding.viewPager.adapter?.registerAdapterDataObserver(viewModel.adapterDataObserver)
    }

    override fun onStop() {
        super.onStop()
        binding.viewPager.adapter?.unregisterAdapterDataObserver(viewModel.adapterDataObserver)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}