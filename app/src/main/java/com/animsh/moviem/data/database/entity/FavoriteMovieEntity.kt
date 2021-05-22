package com.animsh.moviem.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animsh.moviem.models.movie.Movie
import com.animsh.moviem.util.Constants.Companion.FAVORITE_MOVIES_TABLE

/**
 * Created by animsh on 5/22/2021.
 */
@Entity(tableName = FAVORITE_MOVIES_TABLE)
class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Movie
)