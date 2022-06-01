package com.demo.freshworkstudiopractical.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.NetworkResult
import com.demo.freshworkstudiopractical.utils.Utils
import com.paginate.Paginate
import timber.log.Timber
import java.util.*

abstract class BaseFragment() : Fragment() {
    var mContext: Context? = null
    var showLifeCycleLog = false
    protected var loading = false
    protected var totalItem = 0
    protected var paginate: Paginate? = null
    val DELAY: Long = 800 // Milliseconds
    var timer = Timer()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (showLifeCycleLog)
            Timber.d("======onViewCreated")
    }


    override fun onDestroyView() {
        super.onDestroyView()

        if (showLifeCycleLog)
            Timber.d("======onDestroyView")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (showLifeCycleLog)
            Timber.d("======onAttach")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (showLifeCycleLog)
            Timber.d("======onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Utils.hideKeyboard(requireActivity())
        if (showLifeCycleLog)
            Timber.d("======onDetach")
    }

    override fun onResume() {
        super.onResume()
        if (showLifeCycleLog)
            Timber.d("======onResume")
    }


    override fun onPause() {
        super.onPause()
        if (showLifeCycleLog)
            Timber.d("======onPause")
    }

    fun setPaging(recyclerView: RecyclerView, callBack: Paginate.Callbacks): Paginate =
        Paginate.with(recyclerView, callBack)
            .addLoadingListItem(false)
            .build()

    fun shareGif(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
}