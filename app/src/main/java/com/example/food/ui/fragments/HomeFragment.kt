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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.food.databinding.FragmentHomeBinding
import com.example.food.model.Category
import com.example.food.model.Meal
import com.example.food.model.PopularMeal
import com.example.food.ui.Activities.MealDetailsActivity
import com.example.food.ui.adapters.CategoriesMealAdapter
import com.example.food.ui.adapters.PopularMealAdapter
import com.example.food.viewmodel.HomeViewModel



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

        setAdapterToRecViewPopularMeals()
        setAdapterToRecViewCategoriesMeal()
        homeViewModel.getRandomMeal()
        homeViewModel.getPopularMeals()
        homeViewModel.getCategoryMeals()
        observeRandomMeal()
        onRandomMealClick()
        observePopularMeals()
        onPopularItemClicked()
        observeCategoryMealList()
    }


    private fun onPopularItemClicked() {
        popularMealAdapter.onItemClick = { _popularMeal ->
            val intent = Intent(activity,MealDetailsActivity::class.java)
            intent.putExtra(MEAL_ID,_popularMeal.idMeal)
            intent.putExtra(MEAL_Name,_popularMeal.strMeal)
            intent.putExtra(MEAL_Img,_popularMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun setAdapterToRecViewPopularMeals() {
        binding.recViewPopularMeals.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularMealAdapter
        }
    }

    private fun setAdapterToRecViewCategoriesMeal() {
        binding.recViewCategory.apply {
            layoutManager = GridLayoutManager(activity,3,RecyclerView.VERTICAL,false)
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
           popularMealAdapter.setPopularMealListList(it as ArrayList<PopularMeal>)
        }

    }
    private fun observeCategoryMealList(){
        homeViewModel.observeCategoryMealList().observe(viewLifecycleOwner){
            categoriesMealAdapter.setCategoryList(it as ArrayList<Category>)
        }

    }
}