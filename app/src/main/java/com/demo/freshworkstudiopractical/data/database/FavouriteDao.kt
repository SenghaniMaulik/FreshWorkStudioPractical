package com.demo.freshworkstudiopractical.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity

@Dao
interface FavouriteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavourite(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_table")
    fun getFavouriteList(): LiveData<List<FavoritesEntity>>

    @Delete
    suspend fun deleteFavourite(favoritesEntity: FavoritesEntity)

    @Query("SELECT * FROM favorite_table WHERE id = :id LIMIT 1")
    suspend fun getFavourite(id: String): FavoritesEntity?

    @Query("DELETE FROM favorite_table")
    suspend fun deleteAllFavourites()


}
















