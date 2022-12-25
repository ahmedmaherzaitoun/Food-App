package com.example.food.data.db

import com.example.food.model.Meal
import javax.inject.Inject
import javax.inject.Singleton

class DatabaseRepository @Inject constructor(private val mealDB: MealDB){

    suspend fun insertFavoriteMeal(meal: Meal) =  mealDB.mealDao().insertFavoriteMeal(meal)
    suspend fun deleteFavoriteMeal(meal: Meal) = mealDB.mealDao().deleteFavoriteMeal(meal)
    fun getAllFavoriteMeals() = mealDB.mealDao().getAllFavoriteMeals()
    fun checkMealIsFavorite(idMeal :String) =mealDB.mealDao().checkMealIsFavorite(idMeal)
}