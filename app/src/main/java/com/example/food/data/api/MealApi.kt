package com.example.food.data.api

import com.example.food.model.CategoryList
import com.example.food.model.MealList
import com.example.food.model.PopularMealList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    suspend fun getRandomMeal(): Response<MealList>
    @GET("lookup.php?")
    suspend fun getMealDetails(@Query("i") id:String):Response<MealList>
    @GET("filter.php")
    suspend fun getPopularMeals(@Query("a") mealStr:String): Response<PopularMealList>
    @GET("categories.php")
    suspend fun getCategoriesMeals():Response<CategoryList>
    @GET("filter.php")
    fun getCategoryMeals(@Query("c") categoryStr: String):Call<PopularMealList>
}