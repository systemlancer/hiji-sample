package com.example.gallerysample.observers

import androidx.recyclerview.widget.RecyclerView

object PhotoAdapterDataObserver: RecyclerView.AdapterDataObserver() {
    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
        if(positionStart == 0) {

        }
    }
}