package com.example.food.di

import com.example.food.data.api.MealApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun getInstance() : MealApi {
        return Retrofit.Builder().
        baseUrl("https://www.themealdb.com/api/json/v1/1/").
        addConverterFactory(GsonConverterFactory.create()).
        build().create(MealApi::class.java)
    }
}