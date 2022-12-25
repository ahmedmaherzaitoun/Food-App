package com.example.food.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.MealItemBinding
import com.example.food.model.Meal
import com.example.food.model.PopularMeal

class FavoritesAdapter:RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>(){
    lateinit var onItemClick:(Meal)->Unit

    private val differCallback= object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }
    // tool that will take the two list and tell the differences
    val differ= AsyncListDiffer(this, differCallback)

    class FavoritesViewHolder( val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView)
            .load(meal.strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMealName.text = meal.strMeal
        holder.itemView.setOnClickListener {
            onItemClick.invoke(meal)
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}