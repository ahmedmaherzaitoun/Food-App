package com.example.food.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide

import com.example.food.databinding.FragmentHomeBinding
import com.example.food.model.Meal
import com.example.food.ui.Activities.MealDetailsActivity
import com.example.food.viewmodel.HomeViewModel



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var randomMeal: Meal
    companion object{
        const val MEAL_ID = "com.example.food.ui.fragments.idMeal"
        const val MEAL_Name = "com.example.food.ui.fragments.nameMeal"
        const val MEAL_Img = "com.example.food.ui.fragments.imgMeal"

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        homeViewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

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
}