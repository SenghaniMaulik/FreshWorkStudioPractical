package com.demo.freshworkstudiopractical.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.adapter.GifListAdapter
import com.demo.freshworkstudiopractical.common.AdapterClickListener
import com.demo.freshworkstudiopractical.data.database.FavouriteDao
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity
import com.demo.freshworkstudiopractical.databinding.FragmentGifListingBinding
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.NetworkResult
import com.demo.freshworkstudiopractical.utils.Utils
import com.demo.freshworkstudiopractical.utils.toast
import com.demo.freshworkstudiopractical.viewmodel.GifListingFragmentViewModel
import com.demo.freshworkstudiopractical.viewmodel.GifListingFragmentViewModelTest
import com.paginate.Paginate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.isNullOrEmpty as isNullOrEmpty1

@AndroidEntryPoint
class GifListingFragment : BaseFragment(), Paginate.Callbacks {

    @Inject
    lateinit var favouriteDao: FavouriteDao

    private var _binding: FragmentGifListingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GifListingFragmentViewModelTest
    private var offset = 0
    private var hasSearchedImages = false
    private var searchedText: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this)[GifListingFragmentViewModelTest::class.java]
        setObserver()
    }

    private lateinit var gifListAdapter: GifListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (_binding == null) {
            _binding = FragmentGifListingBinding.inflate(inflater, container, false)
            setData()
        }
        return _binding?.root ?: binding.root
    }

    private fun setData() {
        callApiWithCondition(true)
        setOnClicks()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            callApiWithCondition(true)
        }
    }


    private fun setOnClicks() {
        binding.etSearch.doAfterTextChanged {
            timer.cancel()
            timer = Timer()
            timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        Utils.hideKeyboard(requireActivity())
                        if (binding.etSearch.text.toString().isNotEmpty()) {
                            // Reset
                            offset = 0
                            searchedText = binding.etSearch.text.toString()
                            viewModel.getSearchGif(searchedText, offset)
                            hasSearchedImages = true
                        } else {
                            offset = 0
                            searchedText = ""
                            hasSearchedImages = false
                            viewModel.getTrendingGif(offset)
                        }
                    }
                },
                DELAY
            )
        }
    }


    private fun setupGifAdapter() {
        paginate?.unbind()
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
        paginate = setPaging(binding.recyclerView, this)
    }

    private fun shareGif(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }


    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.trendingGifList.observe(this@GifListingFragment) {
                showProgressBar(false)
                when (it) {
                    is NetworkResult.Loading -> {
                        showProgressBar(true)
                    }
                    is NetworkResult.Success -> {
                        it.data?.let { data ->
                            showProgressBar(false)
                            if (!data.data.isNullOrEmpty1()) {
                                data.data?.let {
                                    if (offset==0)
                                        setupGifAdapter()
                                    gifListAdapter.addData(it, offset == 0)
                                }
                                offset = gifListAdapter.list.size
                            } else {
                                gifListAdapter.addData(arrayListOf(), offset == 0)
                            }
                            totalItem = it.data.pagination?.total_count ?: 0
                            loading = false
                        }
                    }
                    is NetworkResult.Error -> {
                        showProgressBar(false)
                        it.message?.toast(requireContext())
                    }
                }
            }

            viewModel.searchGifList.observe(this@GifListingFragment) {
                showProgressBar(false)
                when (it) {
                    is NetworkResult.Loading -> {
                        showProgressBar(true)
                    }
                    is NetworkResult.Success -> {
                        it.data?.let { data ->
                            showProgressBar(false)

                            if (!data.data.isNullOrEmpty1()) {
                                data.data?.let {
                                    if (offset==0)
                                        setupGifAdapter()

                                    gifListAdapter.addData(it, offset == 0) }
                                offset = gifListAdapter.list.size

                            } else {
                                gifListAdapter.addData(arrayListOf(), offset == 0)
                                offset = gifListAdapter.list.size
                            }
                            totalItem = it.data.pagination?.total_count ?: 0
                            loading = false
                        }
                    }
                    is NetworkResult.Error -> {
                        showProgressBar(false)
                        it.message?.toast(requireContext())
                    }
                }
            }
        }
    }

    private fun showProgressBar(isShow: Boolean) {
        binding.swipeRefresh.isRefreshing = isShow
    }

    private fun callApiWithCondition(isReset: Boolean = false) {
        if (isReset) {
            offset = 0
            searchedText = ""
        }
        if (hasSearchedImages) {
            viewModel.getSearchGif(searchedText, offset)
        } else {
            viewModel.getTrendingGif(offset)
        }

    }


    override fun onLoadMore() {
        loading = true
        callApiWithCondition()
    }

    override fun isLoading(): Boolean {
        return loading
    }

    override fun hasLoadedAllItems(): Boolean {
        return gifListAdapter.list.size >= totalItem

    }


}