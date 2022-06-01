package com.demo.freshworkstudiopractical.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.demo.freshworkstudiopractical.MyApplication
import com.demo.freshworkstudiopractical.R
import com.demo.freshworkstudiopractical.data.Repository
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
class GifListingFragmentViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var mContext = application

    private val trandingResponse = "tranding_response.json"

    private val _trendingGifList =
        MutableLiveData<NetworkResult<List<GifResponseModel.GitDataModel>>>(NetworkResult.Loading())
    val trendingGifList: LiveData<NetworkResult<List<GifResponseModel.GitDataModel>>> get() = _trendingGifList


    fun getTrendingGif(
        offset: String
    ) {

        var gitResponseModel =
            Gson().fromJson(trandingResponse.assetJSONFile(mContext), GifResponseModel::class.java)

        viewModelScope.launch {
            try {
                _trendingGifList.value = NetworkResult.Loading()
                // online data check if internet if available
                if (mContext.checkForInternetConnection()) {
                    /*val response = repository.remote.getTrendingGif(offset)
                    response.body()?.let {

                        if (it.meta.status == Constant.API_RESPONSE_STATUS.OK) {
                            it.data?.let { data ->
                                _trendingGifList.value = NetworkResult.Success(
                                    data
                                )
                            }
                        } else {
                            setErrorMessage(it.meta.msg)
                        }
                    }*/
                    _trendingGifList.value = NetworkResult.Success(
                        gitResponseModel.data!!
                    )
                } else {
                    setErrorMessage(mContext.getString(R.string.no_internet))
                }
            } catch (e: Exception) {
                Timber.e(e.localizedMessage)
                setErrorMessage(mContext.getString(R.string.something_went_wrong))
            }
        }
    }

    private fun setErrorMessage(message: String?) {
        _trendingGifList.value = NetworkResult.Error(message)
    }
}