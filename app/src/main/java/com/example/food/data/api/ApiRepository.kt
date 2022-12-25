package com.example.food.data.api

import javax.inject.Inject

class ApiRepository @Inject constructor(private val api: MealApi) {
     suspend fun getRandomMeal() = api.getRandomMeal()
     suspend fun getPopularMeals(mealStr:String) = api.getPopularMeals(mealStr)
     suspend fun getCategoriesMeals() =  api.getCategoriesMeals()
     suspend fun getMealDetails(idMeal:String) = api.getMealDetails(idMeal)
}