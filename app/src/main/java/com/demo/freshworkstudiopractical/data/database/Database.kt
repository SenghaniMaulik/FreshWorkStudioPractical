package com.demo.freshworkstudiopractical.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.freshworkstudiopractical.data.database.entities.FavoritesEntity

@Database(
    entities = [FavoritesEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun shoppingDao(): FavouriteDao
}