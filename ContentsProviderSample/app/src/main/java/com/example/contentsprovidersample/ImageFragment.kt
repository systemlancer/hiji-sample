package com.example.contentsprovidersample

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.contentsprovidersample.databinding.FragmentImageBinding
import timber.log.Timber

class ImageFragment: Fragment() {

    private lateinit var binding: FragmentImageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        binding.fileSelectButton.setOnClickListener {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val volumeNames = MediaStore.getExternalVolumeNames(requireContext())
                Timber.d("sample $volumeNames ${MediaStore.VOLUME_EXTERNAL} ${MediaStore.VOLUME_EXTERNAL_PRIMARY} ${MediaStore.Images.Media.EXTERNAL_CONTENT_URI}")
                val uri = MediaStore.Images.Media.getContentUri(volumeNames.last())
                Timber.d("sample sd card uri $uri")
                val file = requireContext().getExternalFilesDir(Environment)
                Timber.d("sample path ${file?.absolutePath}")
            }
//            val intent = Intent(Intent.ACTION_GET_CONTENT)
//            intent.type = JPEG
//            startActivityForResult(Intent.createChooser(intent,"Select JPEG File"), REQUEST_GET_FILE)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_GET_FILE) {
            if(resultCode == Activity.RESULT_OK && null != data) {
                val uri = data.data
            }
        }
    }

    private fun insert(uri: Uri) {
        val contentProvider = requireContext().contentResolver
        val contentValues = ContentValues()
            .apply {

            }
    }

    companion object {
        const val REQUEST_GET_FILE = 1001
        const val JPEG = "image/jpeg"
    }
}