package com.demo.freshworkstudiopractical.data

import android.app.Application
import com.demo.freshworkstudiopractical.data.network.ApiInterface
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiInterface: ApiInterface,
    val application: Application,
) {

    suspend fun getTrendingGif(offset: String): Response<GifResponseModel> {
        return apiInterface.getTrendingGif(offset)
    }

    suspend fun getSearchGif(query: String,offset: String): Response<GifResponseModel> {
        return apiInterface.getSearchGif(query,offset)
    }


}