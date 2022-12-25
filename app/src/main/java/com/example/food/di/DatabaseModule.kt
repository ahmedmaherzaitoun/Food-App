package com.example.food.di

import android.content.Context
import androidx.room.Room
import com.example.food.data.db.MealDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MealDB{
        return Room.databaseBuilder(context, MealDB::class.java, "meal.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}