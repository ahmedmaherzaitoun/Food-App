package com.example.food.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.CategoryItemBinding
import com.example.food.model.Category
import com.example.food.model.PopularMeal

class CategoriesMealAdapter:RecyclerView.Adapter<CategoriesMealAdapter.CategoryViewHolder>() {
    lateinit var onItemClick:(Category)->Unit

    private val differCallback= object : DiffUtil.ItemCallback<Category>(){
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.idCategory == newItem.idCategory
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
    val categories= AsyncListDiffer(this, differCallback)

    class CategoryViewHolder(var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories.currentList[position]
        holder.binding.tvCategoryName.text = category.strCategory
        Glide.with(holder.itemView)
            .load(category.strCategoryThumb)
            .into(holder.binding.imgCategoryMeal)
        holder.itemView.setOnClickListener {
            onItemClick.invoke(category)
        }
    }

    override fun getItemCount(): Int {
        return categories.currentList.size
    }
}