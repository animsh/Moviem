package com.animsh.moviem.data

import com.animsh.moviem.data.database.FavoriteDao
import com.animsh.moviem.data.database.entity.FavoriteMovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by animsh on 5/22/2021.
 */
class LocalDataSource @Inject constructor(
    private val favoriteDao: FavoriteDao
) {
    // movies
    fun readFavMovies(): Flow<List<FavoriteMovieEntity>> {
        return favoriteDao.readFavMovies()
    }

    suspend fun insertFavMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteDao.insertFavMovie(favoriteMovieEntity)
    }

    suspend fun deleteFavMovie(favoriteMovieEntity: FavoriteMovieEntity) {
        favoriteDao.deleteFavMovie(favoriteMovieEntity)
    }

    suspend fun deleteAllFavMovies() {
        favoriteDao.deleteAllFavMovies()
    }
}