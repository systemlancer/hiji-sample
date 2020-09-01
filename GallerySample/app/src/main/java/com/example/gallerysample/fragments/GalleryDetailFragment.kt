package com.example.gallerysample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.gallerysample.adapters.PhotoAdapter
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
        var photoDetailAdapter: PhotoDetailAdapter? = null
        val binding = FragmentGalleryDetailBinding.inflate(inflater, container, false)
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

        return binding.root
    }
}