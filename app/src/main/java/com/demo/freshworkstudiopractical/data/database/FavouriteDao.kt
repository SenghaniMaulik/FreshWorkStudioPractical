package com.demo.freshworkstudiopractical.data.database

import androidx.room.*
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity

@Dao
interface FavouriteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavourite(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_table")
    suspend fun getFavouriteList(): List<FavoritesEntity>?


}
















