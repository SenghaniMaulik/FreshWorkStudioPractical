package com.demo.freshworkstudiopractical.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.freshworkstudiopractical.MyApplication
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.data.Repository
import com.demo.freshworkstudiopractical.data.database.FavouriteDao
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
class GifListingFragmentViewModelTest @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var mContext = application

    private val trandingResponse = "tranding_response.json"
    private val searchResponse = "search_response.json"

    private val _trendingGifList =
        MutableLiveData<NetworkResult<GifResponseModel>>()
    val trendingGifList: LiveData<NetworkResult<GifResponseModel>> get() = _trendingGifList

    private val _searchGifList =
        MutableLiveData<NetworkResult<GifResponseModel>>()
    val searchGifList: LiveData<NetworkResult<GifResponseModel>> get() = _searchGifList


    fun getTrendingGif(
        offset: Int
    ) {

        Timber.e("Api Call===>$offset ")

        var gitResponseModel =
            Gson().fromJson(trandingResponse.assetJSONFile(mContext), GifResponseModel::class.java)

        viewModelScope.launch {
            try {
                _trendingGifList.value = NetworkResult.Loading()
                // online data check if internet if available
                if (mContext.checkForInternetConnection()) {
                   /* val response = repository.remote.getTrendingGif(offset.toString())
                    response.body()?.let {

                        if (it.meta.status == Constant.API_RESPONSE_STATUS.OK) {
                            it.let { data ->
                                _trendingGifList.value = NetworkResult.Success(
                                    data
                                )
                            }
                        } else {
                            _trendingGifList.value =
                                NetworkResult.Error(it.meta.msg)
                        }
                    }*/
                    _trendingGifList.value = NetworkResult.Success(
                        gitResponseModel
                    )
                } else {
                    _trendingGifList.value =
                        NetworkResult.Error(mContext.getString(R.string.no_internet))
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                _trendingGifList.value =
                    NetworkResult.Error(mContext.getString(R.string.something_went_wrong))
            }
        }
    }


    fun getSearchGif(
        query: String, offset: Int
    ) {

        Timber.e("Api Call===>$offset  $query")
        var gitResponseModel =
            Gson().fromJson(searchResponse.assetJSONFile(mContext), GifResponseModel::class.java)

        viewModelScope.launch {
            try {
                _searchGifList.value = NetworkResult.Loading()
                // online data check if internet if available
                if (mContext.checkForInternetConnection()) {
                     /*val response = repository.remote.getSearchGif(query,offset.toString())
                     response.body()?.let {

                         if (it.meta.status == Constant.API_RESPONSE_STATUS.OK) {
                             it.let { data ->
                                 _searchGifList.value = NetworkResult.Success(
                                     data
                                 )
                             }
                         } else {
                             _searchGifList.value =
                                 NetworkResult.Error(it.meta.msg)
                         }
                     }*/
                    _searchGifList.value = NetworkResult.Success(
                        gitResponseModel
                    )
                } else {
                    _searchGifList.value =
                        NetworkResult.Error(mContext.getString(R.string.no_internet))
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                _searchGifList.value =
                    NetworkResult.Error(mContext.getString(R.string.something_went_wrong))
            }
        }
    }

    fun insertItemInCart(
        favoritesEntity: FavoritesEntity
    ) {
        viewModelScope.launch {
            repository.local.insertToFavourite(favoritesEntity)
        }
    }

    fun deleteFavourite(
        favoritesEntity: FavoritesEntity
    ) {
        viewModelScope.launch {
            repository.local.deleteFavourite(favoritesEntity)
        }
    }
}