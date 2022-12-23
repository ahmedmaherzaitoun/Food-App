package com.example.food.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.CategoryItemBinding
import com.example.food.model.Category

class CategoriesMealAdapter:RecyclerView.Adapter<CategoriesMealAdapter.CategoryViewHolder>() {
    lateinit var onItemClicked:(Category)->Unit
    private var categoryList = ArrayList<Category>()

    fun setCategoryList(categoryList: ArrayList<Category>){
        this.categoryList = categoryList
        notifyDataSetChanged()
    }
    class CategoryViewHolder(var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.tvCategoryName.text = categoryList[position].strCategory
        Glide.with(holder.itemView)
            .load(categoryList[position].strCategoryThumb)
            .into(holder.binding.imgCategoryMeal)

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }
}