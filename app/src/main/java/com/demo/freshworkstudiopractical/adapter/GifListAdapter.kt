package com.demo.freshworkstudiopractical.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.common.AdapterClickListener
import com.demo.freshworkstudiopractical.data.database.FavouriteDao
import com.demo.freshworkstudiopractical.databinding.ListItemBinding
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.setSafeOnClickListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class GifListAdapter(
    val context: Context,
    val favouriteDao: FavouriteDao,
    val mListener: AdapterClickListener
) :
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
        dataModel.adapterPos = holder.absoluteAdapterPosition
        holder.binding.apply {
            dataModel.apply {
                Glide.with(context).asGif()
                    .load(dataModel.images?.fixed_height_downsampled?.url)
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

                CoroutineScope(Dispatchers.Main).launch {
                    imgFavourite.setImageResource(if (dataModel.id?.let {
                            favouriteDao.getFavourite(
                                it
                            )
                        } == null) R.drawable.ic_baseline_favorite_border_24 else R.drawable.ic_baseline_favorite_24)
                }


                imgGif.setSafeOnClickListener {
                    mListener.onItemClick(it, holder.absoluteAdapterPosition, dataModel)
                }
                imgShare.setSafeOnClickListener {
                    mListener.onItemClick(it, holder.absoluteAdapterPosition, dataModel)
                }

                imgFavourite.setSafeOnClickListener {
                    mListener.onItemClick(it, holder.absoluteAdapterPosition, dataModel)
                }


            }

        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        // https://github.com/bumptech/glide/issues/624#issuecomment-140134792
        // Forget view, try to free resources
        Glide.with(holder.itemView.context).clear(holder.binding.imgGif)
        holder.binding.apply {
            imgGif.setImageDrawable(null)
            // Make sure to show progress when loading new view
            progressGif.show()
        }
        Timber.i("onViewRecycled:\t$holder")
        Timber.i("onViewRecycled:\t${holder.binding.progressGif}")
    }

    override fun getItemCount(): Int = list.size


    fun clear() {
        val size = list.size
        if (size > 0) {
            for (i in 0 until size) list.removeAt(0)

            notifyItemRangeRemoved(0, size)
        }
    }

    fun addData(
        dataList: List<GifResponseModel.GitDataModel>,
        isNew: Boolean = true
    ) {
        if (isNew)
            this.list = ArrayList()

        this.list.addAll(dataList)
        if (isNew)
            notifyDataSetChanged()// used to clear all data when first page api data load
        else
            notifyItemRangeInserted(itemCount, list.size - 1)


    }
}