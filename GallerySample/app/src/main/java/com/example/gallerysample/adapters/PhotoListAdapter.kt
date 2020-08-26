package com.example.gallerysample.adapters

import android.content.Context
import android.database.Cursor
import androidx.cursoradapter.widget.SimpleCursorAdapter

class PhotoListAdapter(
    context: Context,
    layout: Int,
    cursor: Cursor,
    from: Array<String>,
    to: IntArray,
    flags: Int
) : SimpleCursorAdapter(context, layout, cursor, from, to, flags) {

}