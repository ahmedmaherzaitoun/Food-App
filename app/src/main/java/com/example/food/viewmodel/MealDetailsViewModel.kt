package com.example.food.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.food.data.RetrofitInstance
import com.example.food.model.Meal
import com.example.food.model.MealList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailsViewModel: ViewModel() {
    private var mealMutableLiveData = MutableLiveData<Meal>()

    fun getMealDetails(id:String){

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
                TODO("Not yet implemented")
                Log.d("Zatona", "onFailure:${t.message} ")
            }

        })

    }

    fun observeMealDetails(): LiveData<Meal> {
        return mealMutableLiveData
    }

}