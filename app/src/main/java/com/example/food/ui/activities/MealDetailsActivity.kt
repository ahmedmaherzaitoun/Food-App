package com.example.food.ui.activities

import android.content.Intent
import android.net.Uri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.example.food.R
import com.example.food.databinding.ActivityMealDetailsBinding
import com.example.food.model.Meal
import com.example.food.ui.fragments.HomeFragment
import com.example.food.viewmodel.MealDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMealDetailsBinding
    private lateinit var mealName:String
    private lateinit var mealID:String
    private lateinit var mealImgPath:String
    private lateinit var mealYoutube:String
    private var meal: Meal? = null
    private var isFavo = 0


    private val mealDetailsViewModel: MealDetailsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealDetailFromIntent()
        setMealDetail()
        binding.imgYoutube.visibility = View.INVISIBLE
        binding.actionBtnFavorite.visibility = View.INVISIBLE

        observeMealDetails()
        onFavoriteClick()
    }
    private fun onFavoriteClick(){
        binding.actionBtnFavorite.setOnClickListener{
           meal?.let{
               if(isFavo ==1){
                   isFavo = 0
                   mealDetailsViewModel.deleteMealFromDB(it)
                   binding.actionBtnFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_favorite_border,null))
                   Toast.makeText(this,"Meal deleted",Toast.LENGTH_SHORT).show()

               }else if (isFavo ==0){
                   it.isFavorite = 1
                   isFavo = 0
                   mealDetailsViewModel.insertMealIntoDB(it)
                   binding.actionBtnFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_favorite,null))
                   observeCheckMealIsFavoriteLiveData()
                   Toast.makeText(this,"Meal saved",Toast.LENGTH_SHORT).show()
               }

           }
        }
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
        observeCheckMealIsFavoriteLiveData()


    }
    private fun observeMealDetails(){
        mealDetailsViewModel.getMealDetails(mealID)
        mealDetailsViewModel.observeMealDetails().observe(this) {
            it.isFavorite = isFavo
            val _meal = it
            meal  = _meal
            onYoutubeClick()
            binding.tvCategory.text = _meal.strCategory
            binding.tvCountryMeal.text = _meal.strArea
            binding.tvStepsContent.text = _meal.strInstructions
            mealYoutube = _meal.strYoutube
            binding.imgYoutube.visibility = View.VISIBLE
            binding.actionBtnFavorite.visibility = View.VISIBLE


        }

    }
    private fun observeCheckMealIsFavoriteLiveData() {
        mealDetailsViewModel.checkMealIsFavorite(mealID).observe(this){
            if(it == 1){
                isFavo = 1
                binding.actionBtnFavorite.setImageDrawable(ResourcesCompat.getDrawable(resources,R.drawable.ic_favorite,null))
            }
        }
    }


}