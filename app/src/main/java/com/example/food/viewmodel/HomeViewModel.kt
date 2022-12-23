package com.example.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.food.data.RetrofitInstance
import com.example.food.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {
    private var mealMutableLiveData = MutableLiveData<Meal>()
    private var popularMealListMutableLiveData = MutableLiveData<List<PopularMeal>>()
    private var categoryMealListMutableLiveData = MutableLiveData<List<Category>>()


    fun getRandomMeal(){

         RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
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
                 TODO("Not yet implemented")
                 Log.d("Zatona", "onFailure:${t.message} ")
             }


         })

     }
    fun getPopularMeals(){
        RetrofitInstance.api.getPopularMeals("Egyptian").enqueue(object: Callback<PopularMealList>{
            override fun onResponse(call: Call<PopularMealList>, response: Response<PopularMealList>) {
                if(response.body() != null){
                        popularMealListMutableLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<PopularMealList>, t: Throwable) {
                Log.d("zatona", "onFailure: ${t.message}")
            }

        })
    }
    fun getCategoryMeals(){
       RetrofitInstance.api.getCategoriesMeals().enqueue(object: Callback<CategoryList>{
           override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               if(response.body() != null){
                   categoryMealListMutableLiveData.value = response.body()!!.categories
               }
           }
           override fun onFailure(call: Call<CategoryList>, t: Throwable) {
               Log.d("zatona", "onFailure: ${t.message}")
           }

       })
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