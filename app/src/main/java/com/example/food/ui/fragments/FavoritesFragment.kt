package com.example.food.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.food.databinding.FragmentFavoritesBinding
import com.example.food.ui.activities.MealDetailsActivity
import com.example.food.ui.adapters.FavoritesAdapter
import com.example.food.viewmodel.FavoritesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Objects

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val favoritesViewModel: FavoritesViewModel by viewModels()
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var favoritesAdapter: FavoritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavoriteMealsLiveData()
        onFavoriteMealClick()
        swipeMealToDelete()

    }

    private fun swipeMealToDelete() {

        val itemTouchHelper = object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val mealDeleted = favoritesAdapter.differ.currentList[position]
                favoritesViewModel.deleteMealFromDB(favoritesAdapter.differ.currentList[position])

                Snackbar.make(requireView() ,"Meal was deleted" ,Snackbar.LENGTH_LONG).setAction(
                    "Undo",
                    View.OnClickListener {
                        favoritesViewModel.insertMealIntoDB(mealDeleted)
                    }
                ).show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavoriteMeals)
    }

    private fun observeFavoriteMealsLiveData() {
       favoritesViewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner){
           favoritesAdapter.differ.submitList(it)
       }
    }
    private fun onFavoriteMealClick() {
        favoritesAdapter.onItemClick = { _Meal ->
            val intent = Intent(activity, MealDetailsActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,_Meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_Name,_Meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_Img,_Meal.strMealThumb)
            startActivity(intent)
        }
    }
    private fun prepareRecyclerView() {
        binding.rvFavoriteMeals.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }
}