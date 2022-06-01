package com.demo.freshworkstudiopractical.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.freshworkstudiopractical.MyApplication
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.data.Repository
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.Constant
import com.demo.freshworkstudiopractical.utils.NetworkResult
import com.demo.freshworkstudiopractical.utils.assetJSONFile
import com.demo.freshworkstudiopractical.utils.checkForInternetConnection
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavouriteGifListingFragmentViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {


    var favouriteList: LiveData<List<FavoritesEntity>> = MutableLiveData()

    var mContext = application

    fun getFavouriteList(
    ) {
        viewModelScope.launch {
            try {
                favouriteList = repository.local.getFavouriteList()

            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
            }
        }
    }

}