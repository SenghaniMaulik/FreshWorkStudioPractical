package com.demo.freshworkstudiopractical.data

import androidx.lifecycle.LiveData
import com.demo.freshworkstudiopractical.data.database.FavouriteDao
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val favouriteDao: FavouriteDao
) {


    suspend fun insertToFavourite(favoritesEntity: FavoritesEntity) {
        return favouriteDao.insertToFavourite(favoritesEntity)
    }

    suspend fun getFavouriteList():  LiveData<List<FavoritesEntity>> {
        return favouriteDao.getFavouriteList()
    }

    suspend fun deleteFavourite(favoritesEntity: FavoritesEntity) {
        return favouriteDao.deleteFavourite(favoritesEntity)
    }

    suspend fun getFavourite(id:String): FavoritesEntity? {
        return favouriteDao.getFavourite(id)
    }

    suspend fun deleteAllFavourites() {
        return favouriteDao.deleteAllFavourites()
    }
}