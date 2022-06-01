package com.demo.freshworkstudiopractical.data.database

import androidx.room.TypeConverter
import com.demo.freshworkstudiopractical.model.response.GifResponseModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    var gson = Gson()

    @TypeConverter
    fun gitDataToString(result: GifResponseModel.GitDataModel): String {
        return gson.toJson(result)
    }

    @TypeConverter
    fun stringToGifData(data: String): GifResponseModel.GitDataModel {
        val listType = object : TypeToken<GifResponseModel.GitDataModel>() {}.type
        return gson.fromJson(data, listType)
    }
}