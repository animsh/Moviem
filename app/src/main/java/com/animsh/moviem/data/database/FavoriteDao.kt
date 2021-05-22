package com.animsh.moviem.data.database

import androidx.room.*
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import com.animsh.moviem.data.database.entity.FavoriteTVEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by animsh on 5/22/2021.
 */
@Dao
interface FavoriteDao {

    // movies
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("SELECT * FROM favorite_movies_table ORDER BY id ASC")
    fun readFavMovies(): Flow<List<FavoriteMovieEntity>>

    @Delete
    suspend fun deleteFavMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("DELETE FROM favorite_movies_table")
    suspend fun deleteAllFavMovies()

    // tv

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavTV(favoriteTVEntity: FavoriteTVEntity)

    @Query("SELECT * FROM favorite_tv_table ORDER BY id ASC")
    fun readFavTV(): Flow<List<FavoriteTVEntity>>

    @Delete
    suspend fun deleteFavTV(favoriteTVEntity: FavoriteTVEntity)

    @Query("DELETE FROM favorite_tv_table")
    suspend fun deleteAllFavTVs()
}