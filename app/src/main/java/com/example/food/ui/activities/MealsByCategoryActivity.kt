package com.example.food.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.food.databinding.ActivityMealsByCategoryBinding
import com.example.food.ui.adapters.MealsByCategoryAdapter
import com.example.food.ui.fragments.HomeFragment
import com.example.food.viewmodel.CategoryMealsViewModel

class MealsByCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealsByCategoryBinding
    private lateinit var categoryName:String
    private lateinit var categoryMealsAdapter: MealsByCategoryAdapter
    private  val categoryMealsViewModel:  CategoryMealsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsAdapter = MealsByCategoryAdapter()

        prepareRecyclerView()
        onMealClick()
        getCategoryNameFromIntent()
        categoryMealsViewModel.getMealsByCategory(categoryName)
        observeMealsLiveDataList()
    }

    private fun prepareRecyclerView() {

        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter

        }

    }

    private fun getCategoryNameFromIntent() {
        categoryName = intent.getStringExtra(HomeFragment.CATEGORY_NAME).toString()
        binding.tvCategoryName.text = categoryName
    }
    private fun observeMealsLiveDataList(){
        categoryMealsViewModel.observeMealsLiveDataList().observe(this){
            categoryMealsAdapter.mealsByCategory.submitList(it)
        }
    }
    private fun onMealClick() {
     categoryMealsAdapter.onItemClick = {_meal->
            val intent = Intent(this.applicationContext, MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,_meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_Name,_meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_Img,_meal.strMealThumb)
            startActivity(intent)

        }
    }
}