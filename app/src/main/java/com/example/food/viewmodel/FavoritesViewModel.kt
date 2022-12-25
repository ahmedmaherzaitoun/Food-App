package com.example.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.data.db.DatabaseRepository
import com.example.food.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val databaseRepository: DatabaseRepository):ViewModel() {
    private val favoriteMeals = databaseRepository.getAllFavoriteMeals()

     fun observeFavoriteMealsLiveData(): LiveData<List<Meal>> {
         return favoriteMeals
    }
    fun insertMealIntoDB(meal:Meal){
        viewModelScope.launch {
            databaseRepository.insertFavoriteMeal(meal)
        }
    }
    fun deleteMealFromDB(meal:Meal){
        viewModelScope.launch {
            databaseRepository.deleteFavoriteMeal(meal)
        }
    }
}