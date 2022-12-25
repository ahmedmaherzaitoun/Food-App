package com.example.food.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.MealItemBinding
import com.example.food.model.PopularMeal

class MealsByCategoryAdapter :RecyclerView.Adapter<MealsByCategoryAdapter.CategoryMealsViewHolder>(){
    lateinit var onItemClick:(PopularMeal)->Unit

    private val differCallback= object : DiffUtil.ItemCallback<PopularMeal>(){
        override fun areItemsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: PopularMeal, newItem: PopularMeal): Boolean {
            return oldItem == newItem
        }
    }
    val mealsByCategory= AsyncListDiffer(this, differCallback)


     class CategoryMealsViewHolder(var binding: MealItemBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewHolder {
       return CategoryMealsViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryMealsViewHolder, position: Int) {
        val meal = mealsByCategory.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
        return mealsByCategory.currentList.size
    }

}