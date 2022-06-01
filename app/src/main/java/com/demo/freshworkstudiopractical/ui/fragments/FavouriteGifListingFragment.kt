package com.demo.freshworkstudiopractical.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.adapter.GifListAdapter
import com.demo.freshworkstudiopractical.common.AdapterClickListener
import com.demo.freshworkstudiopractical.data.database.FavouriteDao
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity
import com.demo.freshworkstudiopractical.databinding.FragmentFavouriteGifListingBinding
import com.demo.freshworkstudiopractical.databinding.FragmentGifListingBinding
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.NetworkResult
import com.demo.freshworkstudiopractical.utils.Utils
import com.demo.freshworkstudiopractical.utils.toast
import com.demo.freshworkstudiopractical.viewmodel.FavouriteGifListingFragmentViewModel
import com.demo.freshworkstudiopractical.viewmodel.GifListingFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FavouriteGifListingFragment : Fragment() {

    private var _binding: FragmentFavouriteGifListingBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var favouriteDao: FavouriteDao
    private val viewModel: FavouriteGifListingFragmentViewModel by viewModels()
    private lateinit var gifListAdapter: GifListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentFavouriteGifListingBinding.inflate(inflater, container, false)
            setData()
        }
        return _binding?.root ?: binding.root
    }

    private fun setData() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }
        viewModel.getFavouriteList()
        setObserver()
    }

    private fun setupGifAdapter() {
        gifListAdapter =
            GifListAdapter(requireContext(), favouriteDao, object : AdapterClickListener {
                override fun onItemClick(view: View, pos: Int, any: Any) {

                    var dataModel = any as GifResponseModel.GitDataModel

                    when (view.id) {
                        R.id.imgFavourite -> {
                            CoroutineScope(Dispatchers.IO).launch {
                                dataModel.id?.let {
                                    if (favouriteDao.getFavourite(it) == null) {
                                        favouriteDao.insertToFavourite(
                                            FavoritesEntity(
                                                it,
                                                dataModel
                                            )
                                        )
                                    } else {
                                        favouriteDao.deleteFavourite(
                                            FavoritesEntity(
                                                it,
                                                dataModel
                                            )
                                        )
                                    }
                                }
                            }
                            gifListAdapter.notifyItemChanged(pos)
                        }
                        R.id.imgShare -> {
                            dataModel.url?.let {
                                shareGif(it)
                            }

                        }
                    }
                }
            })
        binding.recyclerView.apply {
            adapter = gifListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }
    }

    private fun shareGif(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun setObserver() {
        viewModel.favouriteList.observe(viewLifecycleOwner) {
            var list = mutableListOf<GifResponseModel.GitDataModel>()
            it.map {
                list.add(it.gitDataModel)
            }
            setupGifAdapter()
            gifListAdapter.addData(list)

            if (list.isNotEmpty()) {
                binding.recyclerView.isVisible = true
                binding.txtEmptyView.isVisible = false
            } else {
                binding.recyclerView.isVisible = false
                binding.txtEmptyView.isVisible = true
            }
        }
    }

    private fun showProgressBar() {
        binding.swipeRefresh.isRefreshing = false

    }

}