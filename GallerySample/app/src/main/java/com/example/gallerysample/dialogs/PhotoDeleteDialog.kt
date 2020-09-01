package com.example.gallerysample.dialogs

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gallerysample.R
import com.example.gallerysample.utilities.deletePhoto
import com.example.gallerysample.viewmodels.GalleryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PhotoDeleteDialog(private val uri: Uri) : DialogFragment() {

    private val viewModel: GalleryViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.confirm))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(R.string.yes) { _, _ ->
                val hasDeletedPhoto = requireContext().contentResolver.deletePhoto(uri)
                if (hasDeletedPhoto) {
                    viewModel.invalidateDataSource()
                    Toast.makeText(context, "画像を削除しました", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "この画像を削除できませんでした", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }


}