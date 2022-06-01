package com.demo.freshworkstudiopractical.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.freshworkstudiopractical.adapter.diffUtils.GifDiffCallback
import com.demo.freshworkstudiopractical.common.AdapterClickListener
import com.demo.freshworkstudiopractical.databinding.ListItemBinding
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import timber.log.Timber

class GifListAdapter(val context: Context, val mListener: AdapterClickListener) :
    RecyclerView.Adapter<GifListAdapter.ViewHolder>() {

    var list = mutableListOf<GifResponseModel.GitDataModel>()

    class ViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = list[holder.absoluteAdapterPosition]
        holder.binding.apply {
            dataModel.apply {
                Glide.with(context).asGif()
                    .load(dataModel.images?.preview_gif?.url)
                    .listener(
                        object : RequestListener<GifDrawable> {
                            override fun onResourceReady(
                                resource: GifDrawable?,
                                model: Any?,
                                target: Target<GifDrawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // Hide progressbar
                                holder.binding.progressGif.hide()
                                Timber.i("onResourceReady:\t$model")
                                return false
                            }
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<GifDrawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                // Hide progressbar
                                holder.binding.progressGif.hide()
                                Timber.e(e, "onLoadFailed:\t$model")
                                return false
                            }
                        }
                    )
                    .into(holder.binding.imgGif)
                    .clearOnDetach()


                imgGif.setOnClickListener {
                    mListener.onItemClick(it, holder.absoluteAdapterPosition, dataModel)
                }
            }

        }
    }

    override fun getItemCount(): Int = list.size


    fun setData(newList: List<GifResponseModel.GitDataModel>) {
        val diffCallback = GifDiffCallback(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}