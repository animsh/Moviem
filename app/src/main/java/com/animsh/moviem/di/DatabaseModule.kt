package com.animsh.moviem.di

import android.content.Context
import androidx.room.Room
import com.animsh.moviem.data.database.FavoriteDatabase
import com.animsh.moviem.util.Constants.Companion.FAVORITE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by animsh on 5/22/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        FavoriteDatabase::class.java,
        FAVORITE_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: FavoriteDatabase) = database.favoriteDao()
}