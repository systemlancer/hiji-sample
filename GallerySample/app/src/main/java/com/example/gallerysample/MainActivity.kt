package com.example.gallerysample

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gallerysample.dialogs.PhotoDeleteDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PhotoDeleteDialog.DELETE_PERMISSION_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Toast.makeText(applicationContext, "この画像を削除できるようになりました.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}