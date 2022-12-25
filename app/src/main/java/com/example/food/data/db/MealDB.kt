package com.example.food.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.food.model.Meal

@Database(entities = [Meal::class],version = 2)
abstract class MealDB :RoomDatabase() {
    abstract fun mealDao():MealDao
    /*
    companion object{
        @Volatile
        var INSTANCE: MealDB? = null

        @Synchronized
        fun getInstance(context: Context) : MealDB{
            if(INSTANCE == null ){

                INSTANCE = Room.databaseBuilder(context, MealDB::class.java, "meal.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDB
        }

    }*/
}