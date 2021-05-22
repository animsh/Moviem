package com.animsh.moviem.data.database

import androidx.room.TypeConverter
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.models.tv.TV
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by animsh on 5/22/2021.
 */
class FavoriteTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun movieToString(movie: Movie): String {
        return gson.toJson(movie)
    }

    @TypeConverter
    fun stringToMovie(data: String): Movie {
        val listType = object : TypeToken<Movie>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun tvToString(tv: TV): String {
        return gson.toJson(tv)
    }

    @TypeConverter
    fun stringToTv(data: String): TV {
        val listType = object : TypeToken<TV>() {}.type
        return gson.fromJson(data, listType)
    }
}