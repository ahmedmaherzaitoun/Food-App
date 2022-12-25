package com.example.food.viewmodel

import android.text.BoringLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.data.api.ApiRepository
import com.example.food.data.db.DatabaseRepository
import com.example.food.model.Meal
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealDetailsViewModel @Inject constructor(private val databaseRepository: DatabaseRepository, private val apiRepository: ApiRepository): ViewModel() {
    private var mealMutableLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id:String){
        CoroutineScope(Dispatchers.IO).launch {
            val response = apiRepository.getMealDetails(id)
            if (response.isSuccessful) {
                mealMutableLiveData.postValue(response.body()!!.meals[0])

            }
        }
            /*
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if( response.isSuccessful){
                    val randomMeal = response.body()!!.meals[0]
                    mealMutableLiveData.value = randomMeal
                    Log.d("Zatona", "onResponse: ${randomMeal.idMeal  + " name"  + randomMeal.strMeal}")
                }else{
                    return
                }
            }
            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Zatona", "onFailure:${t.message} ")
            }

        })
*/
    }

    fun observeMealDetails(): LiveData<Meal> {
        return mealMutableLiveData
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
    fun checkMealIsFavorite(idMeal:String): LiveData<Int> {
       return databaseRepository.checkMealIsFavorite(idMeal)
    }
}