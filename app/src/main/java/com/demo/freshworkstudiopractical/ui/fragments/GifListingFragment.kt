package com.demo.freshworkstudiopractical.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.viewmodel.GifListingFragmentViewModel
import com.demo.freshworkstudiopractical.adapter.GifListAdapter
import com.demo.freshworkstudiopractical.databinding.FragmentGifListingBinding
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.NetworkResult
import com.demo.freshworkstudiopractical.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifListingFragment : Fragment() {

    private var _binding: FragmentGifListingBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GifListingFragmentViewModel by viewModels()

    private val offset = 0

    private lateinit var productListAdapter: GifListAdapter
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
        gifApiCall()
        setupBestsellerAdapter()
        setOnClicks()
    }


    private fun gifApiCall() {
        viewModel.getTrendingGif(offset.toString())
        setObserver()

    }


    private fun setOnClicks() {
    }

    private fun setupBestsellerAdapter() {
        productListAdapter = GifListAdapter(requireContext()) { `view`, `pos`, `object` ->
            when (view.id) {
                R.id.imgGif -> {
                    val data = `object` as GifResponseModel.GitDataModel
                }
            }
        }
        binding.recyclerView.apply {
            adapter = productListAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 2)
        }
    }


    private fun setObserver() {
        viewModel.trendingGifList.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    showProgressBar(true)
                }
                is NetworkResult.Success -> {

                    it.data?.let { list ->
                        productListAdapter.setData(list)
                        showProgressBar(false)
                    }
                }
                is NetworkResult.Error -> {
                    showProgressBar(false)
                    it.message?.toast(requireContext())
                }
            }
        }

    }

    private fun showProgressBar(isShow: Boolean) {

    }

    override fun onResume() {
        super.onResume()
        setObserver()
    }
}