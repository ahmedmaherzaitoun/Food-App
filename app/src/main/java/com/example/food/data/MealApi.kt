package com.example.food.data

import com.example.food.model.CategoryList
import com.example.food.model.MealList
import com.example.food.model.PopularMealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id:String):Call<MealList>
    @GET("filter.php")
    fun getPopularMeals(@Query("a") mealStr:String): Call<PopularMealList>
    @GET("categories.php")
    fun getCategoriesMeals():Call<CategoryList>
}