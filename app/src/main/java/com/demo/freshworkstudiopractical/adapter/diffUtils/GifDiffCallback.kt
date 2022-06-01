package com.demo.freshworkstudiopractical.adapter.diffUtils

import androidx.recyclerview.widget.DiffUtil
import com.demo.freshworkstudiopractical.model.response.GifResponseModel

class GifDiffCallback(
    private val old: List<GifResponseModel.GitDataModel>,
    private val new: List<GifResponseModel.GitDataModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }
}