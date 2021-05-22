package com.animsh.moviem.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animsh.moviem.models.tv.TV
import com.animsh.moviem.util.Constants.Companion.FAVORITE_TV_TABLE

/**
 * Created by animsh on 5/22/2021.
 */
@Entity(tableName = FAVORITE_TV_TABLE)
class FavoriteTVEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: TV
)