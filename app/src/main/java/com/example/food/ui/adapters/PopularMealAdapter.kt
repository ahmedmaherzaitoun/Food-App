package com.example.food.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food.databinding.PopularMealItemBinding
import com.example.food.model.PopularMeal

class PopularMealAdapter:RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {
    lateinit var onItemClick: (PopularMeal)->Unit
    private var popularMealList = ArrayList<PopularMeal>()

    fun setPopularMealListList( popularMealList: ArrayList<PopularMeal>){
        this.popularMealList = popularMealList
        notifyDataSetChanged()
    }
    class PopularMealViewHolder(var binding: PopularMealItemBinding):RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularMealItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(popularMealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(popularMealList[position])
        }
    }

    override fun getItemCount(): Int {
        return popularMealList.size
    }
}