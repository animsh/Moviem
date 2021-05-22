package com.animsh.moviem.data.database

import androidx.room.*
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by animsh on 5/22/2021.
 */
@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies_table ORDER BY id ASC")
    fun readFavMovies(): Flow<List<FavoriteMovieEntity>>

    @Delete
    suspend fun deleteFavMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies_table")
    suspend fun deleteAllFavMovies()
}