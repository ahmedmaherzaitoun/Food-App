package com.example.food.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.food.model.Meal

@Dao
interface MealDao {

    @Insert
    suspend fun insertFavoriteMeal(meal: Meal)
    @Update
    suspend fun updateFavoriteMeal(meal: Meal)
    @Delete
    suspend fun deleteFavoriteMeal(meal: Meal)
    @Query("SELECT * FROM meal")
    fun getAllFavoriteMeals():LiveData<List<Meal>>
    @Query("SELECT isFavorite FROM meal WHERE idMeal=:id")
    fun checkMealIsFavorite(id :String):LiveData<Int>
}