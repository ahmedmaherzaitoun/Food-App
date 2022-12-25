package com.example.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.data.api.RetrofitInstance
import com.example.food.model.PopularMeal
import com.example.food.model.PopularMealList
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
    private var meals =  MutableLiveData<List<PopularMeal>>()



    fun getMealsByCategory(category:String){
        RetrofitInstance.api.getCategoryMeals(category).enqueue(object: Callback<PopularMealList>{
            override fun onResponse(
                call: retrofit2.Call<PopularMealList>,
                response: Response<PopularMealList>
            ) {
                if( response.body() !=null){
                    meals.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: retrofit2.Call<PopularMealList>, t: Throwable) {
                Log.d("zatona", "onFailure: ${t.message}")
            }

        })
    }
    fun observeMealsLiveDataList(): LiveData<List<PopularMeal>>{
        return meals
    }

}