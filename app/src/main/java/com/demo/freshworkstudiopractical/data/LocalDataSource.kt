package com.demo.freshworkstudiopractical.data

import com.demo.freshworkstudiopractical.data.database.FavouriteDao
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val favouriteDao: FavouriteDao
) {


    suspend fun insertToFavourite(list: FavoritesEntity) {
        return favouriteDao.insertToFavourite(list)
    }

    suspend fun getFavouriteList(): List<FavoritesEntity>? {
        return favouriteDao.getFavouriteList()
    }
}