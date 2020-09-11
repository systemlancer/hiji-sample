package com.example.gallerysample.dialogs

import android.app.Dialog
import android.app.RecoverableSecurityException
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.gallerysample.R
import com.example.gallerysample.utilities.deletePhoto
import com.example.gallerysample.utilities.deletePhotoQ
import com.example.gallerysample.viewmodels.GalleryViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PhotoDeleteDialog(private val uri: Uri) : DialogFragment() {

    private val viewModel: GalleryViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.confirm))
            .setMessage(getString(R.string.delete_message))
            .setPositiveButton(R.string.yes) { _, _ ->
                val hasDeletedPhoto =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        try {
                            requireContext().contentResolver.deletePhotoQ(uri)
                        } catch (e: RecoverableSecurityException) {
                            val intentSender = e.userAction.actionIntent.intentSender
                            intentSender?.let {
                                activity?.startIntentSenderForResult(
                                    intentSender,
                                    DELETE_PERMISSION_REQUEST,
                                    null,
                                    0,
                                    0,
                                    0,
                                    null
                                )
                            }
                            null
                        }
                    } else {
                        requireContext().contentResolver.deletePhoto(uri)
                    }

                hasDeletedPhoto?.let {
                    if (it) {
                        viewModel.invalidateDataSource()
                        Toast.makeText(context, "画像を削除しました", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "この画像を削除できませんでした", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }

    companion object {
        const val DELETE_PERMISSION_REQUEST = 0x1033
    }
}