package com.demo.freshworkstudiopractical.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.demo.freshworkstudiopractical.utils.Constant.Companion.FAVORITE_TABLE

@Entity(tableName = FAVORITE_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var gitDataModel: GifResponseModel.GitDataModel // storing data as string to avoid creating multiple tables
)