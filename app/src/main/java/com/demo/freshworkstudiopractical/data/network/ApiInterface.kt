package com.demo.freshworkstudiopractical.data.network

import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.Constant.Companion.API_KEY
import com.demo.freshworkstudiopractical.utils.Constant.Companion.PAGE_LIMIT
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    companion object {
        const val TRENDING = "trending?api_key=${API_KEY}"
        const val SEARCH = "search?api_key=${API_KEY}"
    }

    @GET(TRENDING)
    suspend fun getTrendingGif(
        @Query("offset") offset: String,
        @Query("limit") limit: String = PAGE_LIMIT
    ): Response<GifResponseModel>

    @GET(SEARCH)
    suspend fun getSearchGif(
        @Query("query") query: String,
        @Query("offset") offset: String,
        @Query("limit") limit: String = PAGE_LIMIT
    ): Response<GifResponseModel>

}