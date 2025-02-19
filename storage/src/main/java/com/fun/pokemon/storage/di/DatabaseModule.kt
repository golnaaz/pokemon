package com.`fun`.pokemon.storage.di

import android.content.Context
import androidx.room.Room
import com.`fun`.pokemon.storage.AppDatabase
import com.`fun`.pokemon.storage.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "APP-Database"

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideBudgetDao(appDatabase: AppDatabase): FavoriteDao = appDatabase.pokemonDao()
}