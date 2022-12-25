package com.example.food.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

import com.example.food.databinding.FragmentHomeBinding
import com.example.food.model.Meal
import com.example.food.ui.activities.MealsByCategoryActivity
import com.example.food.ui.activities.MealDetailsActivity
import com.example.food.ui.adapters.CategoriesMealAdapter
import com.example.food.ui.adapters.PopularMealAdapter
import com.example.food.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal
    private lateinit var popularMealAdapter: PopularMealAdapter
    private lateinit var categoriesMealAdapter: CategoriesMealAdapter

    companion object{
        const val MEAL_ID = "idMeal"
        const val MEAL_Name = "nameMeal"
        const val MEAL_Img = "imgMeal"
        const val CATEGORY_NAME = "categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        popularMealAdapter = PopularMealAdapter()
        categoriesMealAdapter = CategoriesMealAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerViewPopularMeals()
        prepareRecyclerViewCategoriesMeal()
        homeViewModel.getRandomMeal()
        homeViewModel.getPopularMeals()
        homeViewModel.getCategoryMeals()
        observeRandomMeal()
        onRandomMealClick()
        observePopularMeals()
        onPopularItemClick()
        onCategoryItemClick()
        observeCategoryMealList()
    }
    private fun onPopularItemClick() {
        popularMealAdapter.onItemClick = { _popularMeal ->
            val intent = Intent(activity,MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID,_popularMeal.idMeal)
            intent.putExtra(MEAL_Name,_popularMeal.strMeal)
            intent.putExtra(MEAL_Img,_popularMeal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun onCategoryItemClick(){
        categoriesMealAdapter.onItemClick = { _category ->
            val intent = Intent(activity,MealsByCategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME,_category.strCategory)
            startActivity(intent)
        }

    }

    private fun prepareRecyclerViewPopularMeals() {
        binding.recViewPopularMeals.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularMealAdapter
        }
    }

    private fun prepareRecyclerViewCategoriesMeal() {
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesMealAdapter
        }
    }

    private fun onRandomMealClick() {
       binding.cardRandomMeal.setOnClickListener {
           val intent = Intent(activity, MealDetailsActivity::class.java)
           intent.putExtra(MEAL_ID,randomMeal.idMeal)
           intent.putExtra(MEAL_Name,randomMeal.strMeal)
           intent.putExtra(MEAL_Img,randomMeal.strMealThumb)
           startActivity(intent)

       }
    }

    private fun observeRandomMeal(){
        homeViewModel.observeRandomMeal().observe(viewLifecycleOwner){
            Glide.with(this@HomeFragment).
            load(it.strMealThumb).
            into(binding.imgRandomMeal)
            randomMeal = it
        }

    }
    private fun observePopularMeals(){
        homeViewModel.observePopularMealList().observe(viewLifecycleOwner){
            popularMealAdapter.popularMeals.submitList(it)
        }

    }
    private fun observeCategoryMealList(){
        homeViewModel.observeCategoryMealList().observe(viewLifecycleOwner){
            categoriesMealAdapter.categories.submitList(it)
        }

    }
}