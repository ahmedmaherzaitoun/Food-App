package com.example.food.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.food.data.api.ApiRepository
import com.example.food.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val apiRepository: ApiRepository):ViewModel() {
    private var mealMutableLiveData = MutableLiveData<Meal>()
    private var popularMealListMutableLiveData = MutableLiveData<List<PopularMeal>>()
    private var categoryMealListMutableLiveData = MutableLiveData<List<Category>>()

     fun getRandomMeal(){
         viewModelScope.launch {

             val response = apiRepository.getRandomMeal()
             if(response.isSuccessful){
                 mealMutableLiveData.postValue(response.body()!!.meals[0])

             }
             /*
             homeRepository.getRandomMeal().enqueue(object : Callback<MealList> {
                 override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                     if (response.isSuccessful) {
                         val randomMeal = response.body()!!.meals[0]
                         mealMutableLiveData.value = randomMeal
                         Log.d(
                             "Zatona",
                             "onResponse: ${randomMeal.idMeal + " name" + randomMeal.strMeal}"
                         )
                     } else {
                         return
                     }
                 }

                 override fun onFailure(call: Call<MealList>, t: Throwable) {
                     Log.d("Zatona", "onFailure:${t.message} ")
                 }
             })*/
         }
     }
     fun getPopularMeals(){
         viewModelScope.launch {

             val response = apiRepository.getPopularMeals("Egyptian")
             if(response.isSuccessful){
                 popularMealListMutableLiveData.postValue(response.body()!!.meals)

             }
            /* homeRepository.getPopularMeals("Egyptian").enqueue(object : Callback<PopularMealList> {
                 override fun onResponse(
                     call: Call<PopularMealList>,
                     response: Response<PopularMealList>
                 ) {
                     if (response.body() != null) {
                         popularMealListMutableLiveData.value = response.body()!!.meals
                     }
                 }

                 override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                     Log.d("zatona", "onFailure: ${t.message}")
                 }

             })*/
         }
    }
     fun getCategoryMeals(){
         viewModelScope.launch {
             val response = apiRepository.getCategoriesMeals()
             if(response.isSuccessful){
                     categoryMealListMutableLiveData.postValue(response.body()!!.categories)
             }
             /*
             homeRepository.getCategoriesMeals().enqueue(object : Callback<CategoryList> {
                 override fun onResponse(
                     call: Call<CategoryList>,
                     response: Response<CategoryList>
                 ) {
                     if (response.body() != null) {
                         categoryMealListMutableLiveData.value = response.body()!!.categories
                     }
                 }

                 override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                     Log.d("zatona", "onFailure: ${t.message}")
                 }

             })*/
         }
    }

    fun observeRandomMeal():LiveData<Meal>{
        return mealMutableLiveData
    }
    fun observePopularMealList():LiveData<List<PopularMeal>>{
        return popularMealListMutableLiveData
    }
    fun observeCategoryMealList():LiveData<List<Category>>{
        return categoryMealListMutableLiveData
    }


}