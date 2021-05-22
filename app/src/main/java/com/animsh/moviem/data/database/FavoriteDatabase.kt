package com.animsh.moviem.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity

/**
 * Created by animsh on 5/22/2021.
 */
@Database(
    entities = [FavoriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(FavoriteTypeConverter::class)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

}