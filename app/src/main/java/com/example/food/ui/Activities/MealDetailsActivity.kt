package com.example.food.ui.Activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.opengl.Visibility
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.food.R
import com.example.food.databinding.ActivityMealDetailsBinding
import com.example.food.model.Meal
import com.example.food.ui.fragments.HomeFragment
import com.example.food.viewmodel.MealDetailsViewModel
import retrofit2.http.Url
import java.net.URL

class MealDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailsBinding
    private lateinit var mealName:String
    private lateinit var mealID:String
    private lateinit var mealImgPath:String
    private lateinit var mealYoutube:String

    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealDetailFromIntent()
        setMealDetail()
        binding.imgYoutube.visibility = View.INVISIBLE
        observeMealDetails()

    }

    private fun onYoutubeClick(){
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mealYoutube))
            startActivity(intent)
        }

    }
    private fun setMealDetail() {
        binding.collapsingToolbar.title = mealName
        Glide.with(this).
                load(mealImgPath).into(binding.imgRandomDetails)


    }
    private fun getMealDetailFromIntent(){
        val intent = this.intent
        mealID = intent.getStringExtra(HomeFragment.MEAL_ID).toString()
        mealName = intent.getStringExtra(HomeFragment.MEAL_Name).toString()
        mealImgPath = intent.getStringExtra(HomeFragment.MEAL_Img).toString()

    }
    private fun observeMealDetails(){
        mealDetailsViewModel.getMealDetails(mealID)
        mealDetailsViewModel.observeMealDetails().observe(this) {
            val meal = it
            onYoutubeClick()
            binding.tvCategory.text = meal.strCategory
            binding.tvCountryMeal.text = meal.strArea
            binding.tvStepsContent.text = meal.strInstructions
            mealYoutube = meal.strYoutube
            binding.imgYoutube.visibility = View.VISIBLE

        }

    }


}